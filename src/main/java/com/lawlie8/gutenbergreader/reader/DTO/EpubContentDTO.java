package com.lawlie8.gutenbergreader.reader.DTO;

import java.util.List;

public class EpubContentDTO {

    private Integer bookId;

    private Integer startPage;

    private Integer endPage;

    private Integer wordsPerPage;

    private List<EpubPage> epubPageList;

    private MetaData metaData;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getWordsPerPage() {
        return wordsPerPage;
    }

    public void setWordsPerPage(Integer wordsPerPage) {
        this.wordsPerPage = wordsPerPage;
    }

    public List<EpubPage> getEpubPageList() {
        return epubPageList;
    }

    public void setEpubPageList(List<EpubPage> epubPageList) {
        this.epubPageList = epubPageList;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
