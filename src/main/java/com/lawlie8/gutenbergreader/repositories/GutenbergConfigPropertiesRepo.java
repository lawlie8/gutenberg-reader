package com.lawlie8.gutenbergreader.repositories;

import com.lawlie8.gutenbergreader.entities.GutenbergConfigProperties;
import com.lawlie8.gutenbergreader.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GutenbergConfigPropertiesRepo extends JpaRepository<GutenbergConfigProperties,Integer> {

    @Query(value = "select `property_value` from `gutenberg_config_properties` where `property_key` = :key",nativeQuery = true)
    public String getValueByKeyName(String key);
}
