package com.library.catalogue.admin.controller;

import com.library.catalogue.admin.enums.FileCategory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AdminHomePage {

    @PostMapping("/upload")
    public String add(@RequestParam("file") MultipartFile file, FileCategory fileCategory) throws IOException {


        return "redirect:/";
    }
    
}
