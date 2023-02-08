package com.library.catalogue.entity.home;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.catalogue.entity.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="featured_schools")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturedSchools extends BaseModel {

    private String schoolName;

    private String county;

    private String description;

    private String state;

    private String alternateNames;

}
