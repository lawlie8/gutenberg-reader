package com.lawlie8.gutenbergreader.reader.epub;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;
import com.lawlie8.gutenbergreader.reader.DTO.EpubContentDTO;
import com.lawlie8.gutenbergreader.reader.DTO.EpubReaderRequestBodyClass;
import com.lawlie8.gutenbergreader.reader.DTO.MetaData;
import com.lawlie8.gutenbergreader.repositories.BlobObjectsRepo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class EpubReaderService {

    @Autowired
    private BlobObjectsRepo blobObjectsRepo;

    Logger log = LoggerContext.getContext().getLogger(this.getClass().getName());

    Map<String, byte[]> zipFileList = new HashMap<String, byte[]>();

    private String osType = System.getProperty("os.name");

    byte[] mimeTypeByteData = {97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 47, 101, 112, 117, 98, 43, 122, 105, 112};

    public EpubContentDTO getEpubContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException, ParseException {
        return populateContent(epubReaderRequestBodyClass);
    }

    private boolean checkManifest(Integer bookId) {
        return true;
    }

    private EpubContentDTO populateContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException, ParseException {
        EpubContentDTO epubContentDTO = new EpubContentDTO();
        epubContentDTO.setBookId(epubReaderRequestBodyClass.getBookId());
        zipFileList = unZipAndReturnFiles(getEpubBytes(epubReaderRequestBodyClass.getBookId()));
        for (String i : zipFileList.keySet()) {
            if (i.equals("mimetype")) {
                byte[] data = zipFileList.get(i);
                if (Arrays.equals(data, mimeTypeByteData)) {
                    log.info("Valid Epub File Detected");
                    String contentFileName = seeContainerXML(zipFileList.get("META-INF/container.xml"));
                    fetchContent(zipFileList.get(contentFileName),epubContentDTO);
                } else {
                    throw new IOException("Data Not a Valid Epub File");
                }
            }

        }
        return new EpubContentDTO();
    }

    private String seeContainerXML(byte[] xmlContainerData) throws IOException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode xmlJsonNode = xmlMapper.readTree(xmlContainerData);
            return xmlJsonNode.get("rootfiles").get("rootfile").get("full-path").toString().replace("\"", "");
        } catch (Exception e) {
            throw new IOException("Container XML doesnt Contain data");
        }
    }

    private EpubContentDTO fetchContent(byte[] contentFileName,EpubContentDTO epubContentDTO) throws IOException, ParseException {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode xmlJsonNode = xmlMapper.readTree(contentFileName);

        //Write Metadata
        epubContentDTO = writeMetadata(xmlJsonNode.get("metadata"), epubContentDTO);
        //Create spine
        epubContentDTO = createSpine(xmlJsonNode.get("spine"), epubContentDTO);

        System.out.println(epubContentDTO);

        return epubContentDTO;
    }

    private EpubContentDTO writeMetadata(JsonNode jsonNode, EpubContentDTO epubContentDTO) throws ParseException {
        MetaData metaData = new MetaData();
        metaData.setTitle(jsonNode.get("title").toString().replace("\"", ""));
        metaData.setAuthor(parseAuthor(jsonNode.get("creator")));
        metaData.setRights(jsonNode.get("rights").toString().replace("\"", ""));
        metaData.setDate(parseEpubDate(jsonNode.get("date")));
        metaData.setLanguage(jsonNode.get("language").toString().replace("\"", ""));
        metaData.setUrl(jsonNode.get("source").toString().replace("\"", ""));
        metaData.setSubject(parseSubject(jsonNode.get("subject")));
        epubContentDTO.setMetaData(metaData);
        return epubContentDTO;
    }

    private String parseAuthor(JsonNode input) {
        String author = "";
        if (input.get("id").toString().replace("\"", "").equals("author_0")) {
            author = input.get("").toString().replace("\"", "");
        }
        return author;
    }

    private Date parseEpubDate(JsonNode input){
        Date date = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            String dateInString = input.toString().replace("\"", "");
            date = formatter.parse(dateInString);
        }catch (Exception e){
            log.error("Exception Occurred While Parsing Date for Epub",e);
        }
        return date;
    }

    private List<String> parseSubject(JsonNode input) {
        List<String> subjects = new ArrayList<>();
        try {
            input.forEach((e) -> subjects.add(String.valueOf(e).replace("\"", "")));
        } catch (Exception e) {
            log.error("Error Occurred While Parsing Subjects for Epub", e);
        }
        return subjects;
    }

    private EpubContentDTO createSpine(JsonNode jsonNode, EpubContentDTO epubContentDTO) {
        
        return epubContentDTO;
    }

    private byte[] getEpubBytes(Integer bookId) {
        return blobObjectsRepo.fetchEpubBlobById(bookId);
    }

    private Map<String, byte[]> unZipAndReturnFiles(byte[] epubByteArray) {
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(epubByteArray));
        try {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                zipFileList.put(zipEntry.getName(), zipInputStream.readAllBytes());
            }
        } catch (Exception e) {
            //log.error(e);
        } finally {
            try {
                zipInputStream.close();
            } catch (Exception e) {
                log.error("Fs", e.getMessage(), e);
            }
        }
        return zipFileList;
    }
}
