package com.lawlie8.gutenbergreader.repositories;

import com.lawlie8.gutenbergreader.entities.BlobObjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobObjectsRepo extends JpaRepository<BlobObjects,Long> {

    @Query(value = "SELECT `asset_data` FROM `blob_objects` WHERE `asset_id` = :bookId AND `asset_type` = 'picture'",nativeQuery = true)
    public byte[] fetchImageBlobById(long bookId);

}
