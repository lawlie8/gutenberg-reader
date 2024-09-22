package com.lawlie8.gutenbergreader.reader.DTO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MetaData {

    private String title;
    private String author;
    private String language;
    private Date date;
    private String rights;
    private List<String> subject;
    private byte[] imageData;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                ", date=" + date +
                ", rights='" + rights + '\'' +
                ", subject=" + subject +
                ", imageData=" + Arrays.toString(imageData) +
                ", url='" + url + '\'' +
                '}';
    }
}
