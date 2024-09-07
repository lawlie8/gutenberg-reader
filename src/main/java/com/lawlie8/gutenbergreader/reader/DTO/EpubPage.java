package com.lawlie8.gutenbergreader.reader.DTO;

import java.sql.Blob;
import java.util.List;

public class EpubPage {

    private Integer pageNumber;

    private List<String> paragraphList;

    private List<byte[]> imageList;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<String> getParagraphList() {
        return paragraphList;
    }

    public void setParagraphList(List<String> paragraphList) {
        this.paragraphList = paragraphList;
    }

    public List<byte[]> getImageList() {
        return imageList;
    }

    public void setImageList(List<byte[]> imageList) {
        this.imageList = imageList;
    }
}
