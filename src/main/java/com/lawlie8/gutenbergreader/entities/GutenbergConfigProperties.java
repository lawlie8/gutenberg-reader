package com.lawlie8.gutenbergreader.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gutenberg_config_properties")
public class GutenbergConfigProperties {


    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
