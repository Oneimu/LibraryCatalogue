package com.library.catalogue.entity.school;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.catalogue.entity.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="school_funds")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolFunds extends BaseModel {

    private String itemNumber;

    private String acreage;

    private String teacherType;

    private String budgetYear;

    private String  totalCost;

    private String negroesContribution;

    private String whitesContribution;

    private String publicContribution;

    private String rosenwaldContribution;


}
