package com.library.catalogue.service.school;

import com.library.catalogue.entity.school.SchoolBuildings;
import com.library.catalogue.entity.school.SchoolFunds;
import com.library.catalogue.expections.NotFoundException;
import com.library.catalogue.repository.school.SchoolFundsRepository;
import com.library.catalogue.util.csvtodb.SchoolFundsToDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolFundsService {
    private final SchoolFundsRepository schoolFundsRepository;

    private final SchoolBuildingsService schoolBuildingsService;

    private final SchoolFundsToDb schoolFundsToDb;


    // when creating a new school funds, query for it school buildings

    public SchoolFunds saveSchoolFunds(SchoolFunds schoolFunds){
        schoolFunds.setSchoolBuildings(
                getSchoolFundsByCat(
                        schoolFunds.getState(), schoolFunds.getCounty(),schoolFunds.getSchoolNames()));
        return schoolFundsRepository.save(schoolFunds);
    }

    public void saveAllSchoolFunds(List<SchoolFunds> schoolFundsList){
        for (SchoolFunds schoolFunds: schoolFundsList){
            saveSchoolFunds(schoolFunds);
        }
 }

    public List<SchoolFunds> getAllSchoolFunds(){
        return schoolFundsRepository.findAll();
    }

    public SchoolFunds getSchoolFundByUID(String uid){

        return findSchoolFundsByUid(uid);

    }

    public SchoolFunds findSchoolFundsByUid(String uid){
        Optional<SchoolFunds> optionalSchoolFunds = schoolFundsRepository.findSchoolFundsByUid(uid);
        if(!optionalSchoolFunds.isPresent()){
            throw new NotFoundException(String.format("School funds with id %s is not found!", uid));
        }
        return optionalSchoolFunds.get();
    }

    public List<SchoolFunds> transferCsvDataToDb() {

        List<SchoolFunds> schoolFundsList = schoolFundsToDb.schoolFundsCvsToDb();
        if (!schoolFundsList.isEmpty()) {
            saveAllSchoolFunds(schoolFundsList);
        }
        return schoolFundsList;
    }

    public List<SchoolFunds> getSchoolFundsByMultipleUid(List<String> uids){
        List<SchoolFunds> schoolBuildings = new ArrayList<>();


        for(String uid: uids){
            schoolBuildings.add(getSchoolFundByUID(uid));
        }
        return schoolBuildings;
    }

    // update and delete

    public void deleteSchoolFunds(String uid){
        SchoolFunds schoolBuilding = getSchoolFundByUID(uid);
        schoolFundsRepository.delete(schoolBuilding);
    }

    public void deleteMultipleSchoolFunds(List<String> uids){
        for (String uid: uids){
            deleteSchoolFunds(uid);
        }
    }

    public List<SchoolBuildings> getSchoolFundsByCat(String state, String county, String schoolNames){
        return schoolBuildingsService.getSchoolBuildingsByListProperties(
                        state, county, schoolNames);
    }


}
