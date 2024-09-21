package com.lawlie8.gutenbergreader.reader.epub;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;
import com.lawlie8.gutenbergreader.reader.DTO.EpubContentDTO;
import com.lawlie8.gutenbergreader.reader.DTO.EpubReaderRequestBodyClass;
import com.lawlie8.gutenbergreader.repositories.BlobObjectsRepo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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

    byte[] mimeTypeByteData = {97,112,112,108,105,99,97,116,105,111,110,47,101,112,117,98,43,122,105,112};

    public EpubContentDTO getEpubContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException {
            return populateContent(epubReaderRequestBodyClass);
    }

    private boolean checkManifest(Integer bookId) {
        return true;
    }

    private EpubContentDTO populateContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException {
        zipFileList = unZipAndReturnFiles(getEpubBytes(epubReaderRequestBodyClass.getBookId()));
        for(String i : zipFileList.keySet()){
           if(i.equals("mimetype")){
               byte[] data = zipFileList.get(i);
               if(Arrays.equals(data,mimeTypeByteData)){
                   log.info("Valid Epub File Detected");
                   String contentFileName = seeContainerXML(zipFileList.get("META-INF/container.xml"));
                   fetchContent(zipFileList.get(contentFileName));
               }
               else{
                   throw new IOException("Data Not a Valid Epub File");
               }
           }

        }
        return new EpubContentDTO();
    }

    private String seeContainerXML(byte[] xmlContainerData) throws IOException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode xmlJsonNode =  xmlMapper.readTree(xmlContainerData);
            return xmlJsonNode.get("rootfiles").get("rootfile").get("full-path").toString();
        }catch (Exception e){
            throw new IOException("Container XML doesnt Contain data");
        }
    }

    private void fetchContent(byte[] contentFileName){

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
