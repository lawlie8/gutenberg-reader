package com.lawlie8.gutenbergreader.reader.epub;

import com.lawlie8.gutenbergreader.reader.DTO.EpubContentDTO;
import com.lawlie8.gutenbergreader.reader.DTO.EpubReaderRequestBodyClass;
import org.springframework.stereotype.Service;

@Service
public class EpubReaderService {

    public EpubContentDTO getEpubContent(EpubReaderRequestBodyClass epubReaderRequestBodyClass){
        return new EpubContentDTO();
    }

}
