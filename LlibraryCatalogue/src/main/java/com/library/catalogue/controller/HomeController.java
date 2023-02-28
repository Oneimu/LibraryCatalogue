package com.library.catalogue.controller;

import com.library.catalogue.entity.home.FileCategoryInfo;
import com.library.catalogue.enums.FileType;
import com.library.catalogue.service.home.FileCategoryService;
import com.library.catalogue.util.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.API_VERSION_1 +"home")
public class HomeController {

    @Autowired
    private final FileCategoryService fileCategoryService;

    @PostMapping(value="file-categories")
    @ResponseStatus(value = HttpStatus.OK)
    public List<FileCategoryInfo> createFileCategories(@Validated @RequestBody List<FileCategoryInfo> fileCategoryInfos){
        return  fileCategoryService.createMultipleFileCategoryInfo(fileCategoryInfos);
    }

    @PostMapping(value="file-category")
    @ResponseStatus(value = HttpStatus.OK)
    public FileCategoryInfo createFileCategory(@Validated @RequestBody FileCategoryInfo fileCategoryInfo){
        return  fileCategoryService.createFileCategoryInfo(fileCategoryInfo);
    }

    @PutMapping("file-category/{fileType}")
    @ResponseStatus(value = HttpStatus.OK)
    public FileCategoryInfo updateFileCategory(@PathVariable FileType fileType, @Validated @RequestBody FileCategoryInfo fileCategoryInfo){
        return fileCategoryService.updateFileCategory(fileType, fileCategoryInfo);
    }
    @DeleteMapping("file-category/{fileType}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFileCategory(@PathVariable FileType fileType){
        fileCategoryService.deleteFileCategory(fileType);
    }

    @GetMapping(value = "all-file-category")
    @ResponseStatus(value = HttpStatus.OK)
    public List<FileCategoryInfo> getAllFileCategories(){
        return  fileCategoryService.getAllFileCategories();

    }

}
