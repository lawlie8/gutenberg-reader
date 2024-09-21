package com.lawlie8.gutenbergreader.reader.DTO;

public class EpubReaderRequestBodyClass {

    private Integer bookId;

    private Integer startPage;

    private Integer endPage;

    private Integer wordsPerPage;

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

    @Override
    public String toString() {
        return "EpubReaderRequestBodyClass{" +
                "bookId=" + bookId +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", wordsPerPage=" + wordsPerPage +
                '}';
    }
}
