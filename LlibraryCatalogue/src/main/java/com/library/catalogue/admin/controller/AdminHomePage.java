package com.library.catalogue.admin.controller;

import com.library.catalogue.admin.enums.FileCategory;
import com.library.catalogue.admin.service.HomePageService;
import com.library.catalogue.entity.admin.AdminInfo;
import com.library.catalogue.util.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.API_VERSION_1 +"admin-landing-page")
public class AdminHomePage {

    private final HomePageService homePageService;

    @PostMapping("/upload/{fileCategory}")
    public String uploadMetadata(@RequestParam("file") MultipartFile file, @PathVariable FileCategory fileCategory) throws IOException {

        homePageService.extractZipFile(file, fileCategory);
        return "";
    }

    public AdminInfo getAdminInfo(){
        return null;
    }

    // delete record

    // update record

    //
}
