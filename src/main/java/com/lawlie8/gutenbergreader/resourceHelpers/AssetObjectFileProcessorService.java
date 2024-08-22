package com.lawlie8.gutenbergreader.resourceHelpers;

import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.entities.BlobObjects;
import com.lawlie8.gutenbergreader.entities.Books;
import com.lawlie8.gutenbergreader.repositories.BlobObjectsRepo;
import com.lawlie8.gutenbergreader.repositories.BooksRepo;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AssetObjectFileProcessorService {

    @Autowired
    GutenbergResourceService gutenbergResourceService;
    private static final String BOOK_TYPE_GUTENBERG_UPLOAD = "gutenberg_upload";
    private static final String GUTENBERG_ZIP_PREFIX = "https://www.gutenberg.org/cache/epub/%s/pg%s-h.zip";
    private static final String GUTENBERG_EBOOK_PREFIX = "https://www.gutenberg.org/ebooks/%s.epub3.images";
    private static final String GUTENBERG_IMAGE_PREFIX = "https://www.gutenberg.org/cache/epub/%s/pg%s.cover.medium.jpg";

    @Autowired
    private BooksRepo booksRepo;

    @Autowired
    private BlobObjectsRepo blobObjectsRepo;

    Logger log = LoggerContext.getContext().getLogger(this.getClass().getName());

    public void processDailyDownloadDataIntoDatabase() throws SQLException, IOException {
        try {
            log.info("Processing Daily Rss Data for date: {}", new Date().toString());
            DailyRssBookDto dailyRssBookDto = gutenbergResourceService.fetchDailyRssBookTitles();
            List<Books> allBooks = new ArrayList<>();
            for (int i = 0; i < dailyRssBookDto.getChannel().getItem().size(); i++) {
                Books books = new Books();
                books.setTitle(dailyRssBookDto.getChannel().getItem().get(i).getTitle().replace("\"", ""));
                books.setBookDescription(dailyRssBookDto.getChannel().getItem().get(i).getDescription().replace("\"", ""));
                books.setBookLanguage(parseBookLanguage(dailyRssBookDto.getChannel().getItem().get(i).getDescription().replace("\"", "")));
                books.setBookType(BOOK_TYPE_GUTENBERG_UPLOAD);
                books.setBookId(parseBookId(dailyRssBookDto.getChannel().getItem().get(i).getLink()));
                books.setAuthor(parseBookAuthor(dailyRssBookDto.getChannel().getItem().get(i).getTitle()));
                books.setUploadDate(new Date());
                books.setYearOfPublication(Year.now());
                try {
                    log.debug("Adding Books Entity : {}", books.toString());
                    booksRepo.save(books);
                }catch (Exception e){
                    log.error("Exception While Saving Book Object :" +e.toString());
                }
                allBooks.add(books);
            }
            saveBlobObjects(allBooks);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception Occurred While Processing Data with error message : ", e.toString());
        }
    }

    private void saveBlobObjects(List<Books> allBooks) throws SQLException, IOException {
        try {
            List<BlobObjects> blobObjectsList = new ArrayList<>();
            for (int i = 0; i < allBooks.size(); i++) {
                Books books = allBooks.get(i);
                blobObjectsList.add(saveZipBlob(books.getBookId()));
                blobObjectsList.add(saveImageBlob(books.getBookId()));
                blobObjectsList.add(saveEbookObject(books.getBookId()));
            }
            for(int i = 0;i<blobObjectsList.size();i++){
                try {
                    blobObjectsRepo.save(blobObjectsList.get(i));
                }catch (Exception e){
                    log.error("Exception Occurred While Saving Blob Object with id" + blobObjectsList.get(i).getBookId());
                }
            }
        } catch (Exception e) {
            log.error("Exception Occurred While Saving Blob Objects with error message : ", e);
        }
    }

    public byte[] getBookCoverForId(Long bookId) {
        return blobObjectsRepo.fetchImageBlobById(bookId);
    }

    public byte[] getZipBlobforDownload(Long assetId) {
        return blobObjectsRepo.fetchZipBlobById(assetId);
    }

    public byte[] getEpubBlobforDownload(Long assetId) {
        return blobObjectsRepo.fetchEpubBlobById(assetId);
    }

    private BlobObjects saveEbookObject(Long bookId) throws IOException, SQLException {

        try {
            BlobObjects blobObjects = new BlobObjects();
            InputStream is = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            URL website = new URL(String.format(GUTENBERG_EBOOK_PREFIX, bookId.toString()));
            is = website.openStream();
            byte[] bytes = null;
            bytes = IOUtils.toByteArray(is);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            String sha1 = bytesToHex(digest.digest(bytes));
            blobObjects.setBookId(bookId);
            blobObjects.setAssetData(bytes);
            blobObjects.setAssetType("epub");
            blobObjects.setAssetUploadType("gutenberg_upload");
            blobObjects.setAssetUploadDate(new Date());
            blobObjects.setAssetSha1Hash(sha1);
            return blobObjects;
        } catch (Exception e) {
            e.printStackTrace();
            //TODO add logs here
            return null;
        }
    }


    private BlobObjects saveImageBlob(Long bookId) throws IOException, SQLException {
        try {
            BlobObjects blobObjects = new BlobObjects();
            InputStream is = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            URL website = new URL(String.format(GUTENBERG_IMAGE_PREFIX, bookId.toString(), bookId.toString()));
            is = website.openStream();
            byte[] bytes = null;
            bytes = IOUtils.toByteArray(is);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            String sha1 = bytesToHex(digest.digest(bytes));
            blobObjects.setBookId(bookId);
            blobObjects.setAssetData(bytes);
            blobObjects.setBookId(bookId);
            blobObjects.setAssetData(bytes);
            blobObjects.setAssetType("picture");
            blobObjects.setAssetUploadType("gutenberg_upload");
            blobObjects.setAssetUploadDate(new Date());
            blobObjects.setAssetSha1Hash(sha1);
            return blobObjects;
        } catch (Exception e) {
            e.printStackTrace();
            //TODO add logs here
            return null;
        }
    }

    private BlobObjects saveZipBlob(Long bookId) throws IOException, SQLException {
        try {
            BlobObjects blobObjects = new BlobObjects();
            InputStream is = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            URL website = new URL(String.format(GUTENBERG_ZIP_PREFIX, bookId.toString(), bookId));
            is = website.openStream();
            byte[] bytes = null;
            bytes = IOUtils.toByteArray(is);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            String sha1 = bytesToHex(digest.digest(bytes));
            blobObjects.setBookId(bookId);
            blobObjects.setAssetData(bytes);
            blobObjects.setBookId(bookId);
            blobObjects.setAssetData(bytes);
            blobObjects.setAssetType("zip");
            blobObjects.setAssetUploadType("gutenberg_upload");
            blobObjects.setAssetUploadDate(new Date());
            blobObjects.setAssetSha1Hash(sha1);
            return blobObjects;
        } catch (Exception e) {
            e.printStackTrace();
            //TODO add logs here
            return null;
        }
    }

    private String parseBookLanguage(String language) {
        return language.replace("Language: ", "");
    }

    private Long parseBookId(String link) {
        int lastSlashIndex = link.lastIndexOf('/');
        String numberString = link.substring(lastSlashIndex + 1);
        return Long.parseLong(numberString.replace("\"", ""));
    }

    private String parseBookAuthor(String title) {
        try {
            int indexOfBy = title.indexOf("by");
            if (indexOfBy != -1) {
                return title.substring(indexOfBy + 2).trim().replace("\"", "");
            } else {
                return "Unknown";
            }
        } catch (Exception e) {
            return "Unkown";
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
