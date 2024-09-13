package com.lawlie8.gutenbergreader.reader.epub;

import com.lawlie8.gutenbergreader.reader.DTO.EpubContentDTO;
import com.lawlie8.gutenbergreader.reader.DTO.EpubReaderRequestBodyClass;
import com.lawlie8.gutenbergreader.repositories.BlobObjectsRepo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class EpubReaderService {

    @Autowired
    private BlobObjectsRepo blobObjectsRepo;

    Logger log = LoggerContext.getContext().getLogger(this.getClass().getName());

    Map<String, File> zipFileList = new HashMap<>();

    public EpubContentDTO getEpubContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException {
            return populateContent(epubReaderRequestBodyClass);
    }

    private boolean checkManifest(Integer bookId) {
        return true;
    }

    private EpubContentDTO populateContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException {
        zipFileList = unZipAndReturnFiles(getEpubBytes(epubReaderRequestBodyClass.getBookId()));
        for(String i : zipFileList.keySet()){
           System.out.println(zipFileList.get(i).getName());
           if(i.equals("mimetype")){
               System.out.println(new FileInputStream(zipFileList.get(i)).readAllBytes().toString());
           }

        }
        return new EpubContentDTO();
    }

    private byte[] getEpubBytes(Integer bookId) {
        return blobObjectsRepo.fetchEpubBlobById(bookId);
    }

    private Map<String, File> unZipAndReturnFiles(byte[] epubByteArray) {
            ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(epubByteArray));
            try {
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    File destFile = new File(zipEntry.getName());
                    FileOutputStream os = new FileOutputStream(destFile);
                    for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
                        os.write(c);
                    }
                    os.close();
                    zipFileList.put(zipEntry.getName(), destFile);
                }
            } catch (Exception e) {
                log.error(e);
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
