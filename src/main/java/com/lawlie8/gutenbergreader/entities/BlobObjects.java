package com.lawlie8.gutenbergreader.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "blob_objects")
public class BlobObjects {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_id")
    private Long bookId;

    @Column(name = "asset_data")
    private Blob assetData;

    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "asset_upload_type")
    private String assetUploadType;

    @Column(name = "asset_upload_date")
    private Date assetUploadDate;

    @Column(name = "asset_sha1_hash")
    private String assetSha1Hash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Blob getAssetData() {
        return assetData;
    }

    public void setAssetData(Blob assetData) {
        this.assetData = assetData;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetUploadType() {
        return assetUploadType;
    }

    public void setAssetUploadType(String assetUploadType) {
        this.assetUploadType = assetUploadType;
    }

    public Date getAssetUploadDate() {
        return assetUploadDate;
    }

    public void setAssetUploadDate(Date assetUploadDate) {
        this.assetUploadDate = assetUploadDate;
    }

    public String getAssetSha1Hash() {
        return assetSha1Hash;
    }

    public void setAssetSha1Hash(String assetSha1Hash) {
        this.assetSha1Hash = assetSha1Hash;
    }
}
