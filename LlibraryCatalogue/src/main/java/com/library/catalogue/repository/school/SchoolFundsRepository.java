package com.library.catalogue.repository.school;

import com.library.catalogue.entity.school.SchoolBuildings;
import com.library.catalogue.entity.school.SchoolFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolFundsRepository extends JpaRepository<SchoolFunds, String> {

    Optional<SchoolFunds> findSchoolFundsByUid(String uid);

}
