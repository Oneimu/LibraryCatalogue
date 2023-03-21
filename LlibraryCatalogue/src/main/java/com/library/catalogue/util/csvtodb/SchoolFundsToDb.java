package com.library.catalogue.util.csvtodb;


import com.library.catalogue.dto.SchoolFundsDto;
import com.library.catalogue.entity.school.SchoolFunds;
import com.library.catalogue.service.school.SchoolBuildingsService;
import com.library.catalogue.util.ReadUrl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SchoolFundsToDb {
    private final String SCHOOL_FUNDS_URL = "https://raw.githubusercontent.com/Aayush000/Rosenwald_Library/main/schools/fund_metadata.csv";

    private ReadUrl readUrl = new ReadUrl();


    private SchoolBuildingsService schoolBuildingsService;


    public List<SchoolFunds> schoolFundsCvsToDb() {

        List<SchoolFunds> allSchoolFunds = new ArrayList<>();

        Iterable<CSVRecord> records = readUrl.readURL(SCHOOL_FUNDS_URL);

        SchoolFundsDto schoolFundsDto;

        for (CSVRecord record : records) {

            schoolFundsDto = new SchoolFundsDto();

            schoolFundsDto.setAlternateNames(record.get("Alternate Names"));
            schoolFundsDto.setAppl(record.get("Appl #"));
            schoolFundsDto.setCounty(record.get("County"));
            schoolFundsDto.setSchoolNames(record.get("School Names"));
            schoolFundsDto.setSchoolCardId(record.get("School Photo ID"));
            schoolFundsDto.setItemNumber(record.get("Item Number"));
            schoolFundsDto.setDescription(record.get("Description"));
            schoolFundsDto.setTitle(record.get("Title"));
            schoolFundsDto.setOriginalDate(record.get("Date.Original"));
            schoolFundsDto.setUid(record.get("UID"));
            schoolFundsDto.setState(record.get("State"));
            schoolFundsDto.setAcreage(record.get("Acreage"));
            schoolFundsDto.setBudgetYear(record.get("Budget Year"));
            schoolFundsDto.setTotalCost(record.get("Total Cost"));
            schoolFundsDto.setTeacherType(record.get("Teacher Type"));
            schoolFundsDto.setBlacksContribution(record.get("Negroes Contribution"));
            schoolFundsDto.setWhitesContribution(record.get("Whites Contribution"));
            schoolFundsDto.setPublicContribution(record.get("Public Contribution"));
            schoolFundsDto.setRosenwaldContribution(record.get("Rosenwald Contribution"));
//            schoolFundsDto.setPhotoId();

            // the list of school buildings that align
            schoolFundsDto.setSchoolBuildings(schoolBuildingsService.getSchoolBuildingsByListProperties(schoolFundsDto.getState(), schoolFundsDto.getCounty(), schoolFundsDto.getSchoolNames()));

            allSchoolFunds.add(schoolFundsDto.toEntity());

        }
        return allSchoolFunds;
    }
}
