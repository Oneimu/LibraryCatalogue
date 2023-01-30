package com.library.catalogue.entity.home;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.catalogue.entity.base.BaseModel;
import com.library.catalogue.enums.FileType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="file_category")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileCategoryInfo extends BaseModel {

    // this is the same as the file name, it is an enum type
    private FileType fileCategory;

    private String description;

    // might be change to list of urls sometime in the future. for now, one image for one category
    private String imageUrl;

}
