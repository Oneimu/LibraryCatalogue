package com.library.catalogue.service.school;


import com.library.catalogue.entity.school.SchoolBuildings;
import com.library.catalogue.expections.NotFoundException;
import com.library.catalogue.repository.school.SchoolBuildingsRepository;
import com.library.catalogue.util.csvtodb.SchoolBuildingsToDb;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolBuildingsService {

    private final SchoolBuildingsRepository schoolBuildingsRepository;

    private final SchoolBuildingsToDb schoolBuildingsToDb;


    // need a class that handles the transfer to db

    public SchoolBuildings saveSchoolBuilding(SchoolBuildings schoolBuilding){
        return schoolBuildingsRepository.save(schoolBuilding);
    }

    public List<SchoolBuildings> saveAllSchoolBuildings(List<SchoolBuildings> schoolBuildings){
        return schoolBuildingsRepository.saveAll(schoolBuildings);
    }

    public List<SchoolBuildings> getAllSchoolBuildings(){
        return StreamEx.of(schoolBuildingsRepository.findAll()).distinct(SchoolBuildings::getTitle).toList();
    }

    public SchoolBuildings getSchoolBuildingByUID(String uid){

        return findSchoolBuildingByUid(uid);

    }

    public SchoolBuildings findSchoolBuildingByUid(String uid){
        Optional<SchoolBuildings> optionalSchoolBuildings = schoolBuildingsRepository.findSchoolBuildingsByUid(uid);
        if(!optionalSchoolBuildings.isPresent()){
            throw new NotFoundException(String.format("School Building with title %s is not found!", uid));
        }
        return optionalSchoolBuildings.get();
    }

    public List<SchoolBuildings> saveCsv() {

        List<SchoolBuildings> schoolBuildingsList = schoolBuildingsToDb.schoolBuildingCvsToDb();
        if (!schoolBuildingsList.isEmpty()) {
            saveAllSchoolBuildings(schoolBuildingsList);
        }
        return schoolBuildingsList;
    }

    public List<SchoolBuildings> getSchoolBuildingByMultipleUid(List<String> uids){
        List<SchoolBuildings> schoolBuildings = new ArrayList<>();

        for(String uid: uids){
            schoolBuildings.add(getSchoolBuildingByUID(uid));
        }
        return schoolBuildings;
    }

    // update and delete
    public void deleteSchoolBuilding(String uid){
        SchoolBuildings schoolBuilding = getSchoolBuildingByUID(uid);
        schoolBuildingsRepository.delete(schoolBuilding);
    }

    public void deleteMultipleSchoolBuilding(List<String> uids){
        for (String uid: uids){
            deleteSchoolBuilding(uid);
        }
    }

}
