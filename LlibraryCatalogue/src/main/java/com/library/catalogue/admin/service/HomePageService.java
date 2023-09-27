package com.library.catalogue.admin.service;

import com.library.catalogue.admin.entity.UploadDataLog;
import com.library.catalogue.admin.enums.FileCategory;
import com.library.catalogue.util.csvtodb.SchoolBuildingsToDb;
import com.library.catalogue.util.csvtodb.SchoolFundsToDb;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final SchoolBuildingsToDb schoolBuildingsToDb;
    private final SchoolFundsToDb schoolFundsToDb;

    Logger logger = LoggerFactory.getLogger(LogRecord.class);

    // temporary save images to a folder in my mac
    // todo: move all links and constant string to properites
    String IMAGES_DIRECTORY = "/Users/luckyabolorunke/Desktop/LibraryCatalogueWebsite/";

    public void extractZipFile(MultipartFile file, FileCategory fileCategory) throws IOException {

        /** save file to temp */
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();

        /** destination where the unzip file would be saved */
        String destination = directory(fileCategory);
        try {
            ZipFile zipFile = new ZipFile(zip);
            // check the zip contains at least an image and a metadata.
            if (checkZipContent(zipFile)){
                // retrieve zip file pass the zip url to
                saveAllCsvToDb(getCsvFromZip(zipFile));

                // extract all the following to destination

                zipFile.extractAll(destination);
            }



        } catch (ZipException e) {
            e.printStackTrace();
        } finally {
            /** delete temp file */
            zip.delete();
        }

    }

    // todo: read through and understand
    public static void extractAll(String zipFilePath, String destinationDirectory) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            File destDir = new File(destinationDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                File entryFile = new File(destinationDirectory, entryName);

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (InputStream is = zipFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(entryFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }

    private boolean checkZipContent(ZipFile zipFile){

        boolean imagePresent = zipFile.stream()
                .map(ZipEntry::getName)
                .anyMatch(name -> name.endsWith(".png")||
                        name.endsWith("jpeg") ||
                        name.endsWith(".tiff") ||
                        name.endsWith("jpg"));
        boolean metadataPresent = zipFile.stream()
                .map(ZipEntry::getName)
                .anyMatch(name -> name.endsWith(".csv")||
                        name.endsWith(".xlsx"));

        // log error: when zip is missing a file
        if (!imagePresent && !metadataPresent){
            logger.error("Both image and metadata files are missing in th zip file!!!");
        }else if (!imagePresent) {
            logger.error("Both image and metadata files are missing in th zip file!!!");
        }else if (!metadataPresent) {
            logger.error("Both image and metadata files are missing in th zip file!!!");
        }

        return imagePresent && metadataPresent;
    }

    private List<String> getCsvFromZip(ZipFile zipFile){
        return zipFile.stream()
                .map(ZipEntry::getName)
                .filter(name -> name.endsWith(".csv")||
                        name.endsWith(".xlsx"))
                .collect(Collectors.toList());
    }

    private int saveAllCsvToDb(List<String> csvFiles){
        for (String csv: csvFiles){
            // todo: if it does not work, pass Iterable<CSVRecord> instead of a csv name

        }
        // todo: change return value
        return 0;
    }

    /** save the csv info into the specific table */
    // check csvfile format is correct
    private int saveCsvToDb(String csvFile, FileCategory fileCategory){
        switch (fileCategory) {
            case SCHOOLBUILDING:
                return schoolBuildingsToDb.schoolBuildingCvsToDbUrl(csvFile).size();
            case SCHOOLFUND:
                return schoolFundsToDb.schoolFundsCvsToDbUrl(csvFile).size();
        }
        return 0;
    }

    private String directory(FileCategory fileCategory){
        return IMAGES_DIRECTORY + fileCategory.name();
    }

    // todo: complete this
    private UploadDataLog getDataLog(){
        return null;
    }


}
