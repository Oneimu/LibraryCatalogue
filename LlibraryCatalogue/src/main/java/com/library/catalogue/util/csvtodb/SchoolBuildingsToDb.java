package com.library.catalogue.util.csvtodb;

import com.library.catalogue.dto.SchoolBuildingsDto;
import com.library.catalogue.entity.school.SchoolBuildings;
import com.library.catalogue.util.ReadUrl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Getter
public class SchoolBuildingsToDb {
    private final String SCHOOL_BUILDING_URL = "https://raw.githubusercontent.com/Aayush000/Rosenwald_Library/main/schools/school_metadata.csv";

    private ReadUrl readUrl = new ReadUrl();

    public List<SchoolBuildings> schoolBuildingCvsToDb() {

        List<SchoolBuildings> allSchoolBuildings = new ArrayList<>();

        Iterable<CSVRecord> records = readUrl.readURL(SCHOOL_BUILDING_URL);

        SchoolBuildingsDto schoolBuildingsDto;

        for (CSVRecord record : records) {

            schoolBuildingsDto = new SchoolBuildingsDto();

            schoolBuildingsDto.setAlternateNames(record.get("Alternate Names"));
            schoolBuildingsDto.setAppl(record.get("Appl #"));
            schoolBuildingsDto.setCounty(record.get("County"));
            schoolBuildingsDto.setSchoolNames(record.get("School Names"));
            schoolBuildingsDto.setSchoolCardId(record.get("School Card ID"));
            schoolBuildingsDto.setPhotoId(record.get("Photo ID #"));
            schoolBuildingsDto.setDescription(record.get("Description"));
            schoolBuildingsDto.setTitle(record.get("Title"));
            schoolBuildingsDto.setOriginalDate(record.get("Date.Original"));
            schoolBuildingsDto.setUid(record.get("UID"));
            schoolBuildingsDto.setState(record.get("State"));

            allSchoolBuildings.add(schoolBuildingsDto.toEntity());

        }
        return allSchoolBuildings;
    }
}
