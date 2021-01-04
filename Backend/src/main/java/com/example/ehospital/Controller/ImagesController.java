package com.example.ehospital.Controller;

import com.example.ehospital.Model.AdminImages;
import com.example.ehospital.Model.DoctorImages;
import com.example.ehospital.Model.UserImages;
import com.example.ehospital.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@CrossOrigin(origins = {"http://localhost:8081"})   // The address is for frontend(vue) running address
@RestController
@RequestMapping(path="/Project/REST-API")
public class ImagesController {

    @Autowired
    private DoctorImages_repository doctorImages_repository;

    @Autowired
    private AdminImages_repository adminImages_repository;

    @Autowired
    private UserImages_repository userImages_repository;

    @Autowired
    private Doctor_repository doctor_repository;

    @Autowired
    private Admin_repository admin_repository;

    @Autowired
    private User_repository user_repository;


    @Transactional
    @PostMapping("/doctor/image/upload/{id}")
    public String uplaodDoctorImage(@RequestParam("imagefile") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        if(doctorImages_repository.isImageExistByDoctorID(id) == 1) {
            doctorImages_repository.updateDoctorImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
            Long imageID = doctorImages_repository.findImageIDByDoctorID(id);
            doctor_repository.updateImageIDByDoctorID(imageID, id);
            return "Image updated successfully";
        }
        doctorImages_repository.insertDoctorImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
        Long imageID = doctorImages_repository.findImageIDByDoctorID(id);
        doctor_repository.updateImageIDByDoctorID(imageID, id);
        return "Image uploaded successfully";
    }


    @Transactional
    @GetMapping(path = { "/doctor/image/get/{id}" })
    public DoctorImages getDoctorImage(@PathVariable("id") Long id) throws IOException {
        if(doctorImages_repository.isImageExistByDoctorID(id) == 1) {
            final Optional<DoctorImages> retrievedImage = doctorImages_repository.findByDoctorDoctorID(id);
            DoctorImages img = new DoctorImages(retrievedImage.get().getName(), retrievedImage.get().getType(), decompressBytes(retrievedImage.get().getpic_byte()));
            img.setDoctor(retrievedImage.get().getDoctor());
            return img;
        }
        return null;
    }


    @Transactional
    @GetMapping(path = { "/doctor/{department}/image" })
    public List<DoctorImages> getDoctorImages(@PathVariable("department") String department) throws IOException {
        if(doctorImages_repository.isImageExistByDepartment(department) > 0) {
            return doctorImages_repository.findByDoctorDepartment(department);
        }
        return null;
    }


    @Transactional
    @PostMapping("/admin/image/upload/{id}")
    public String uplaodAdminImage(@RequestParam("imagefile") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        if(adminImages_repository.isImageExistByAdminID(id) == 1) {
            adminImages_repository.updateAdminImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
            Long imageID = adminImages_repository.findImageIDByAdminID(id);
            admin_repository.updateImageIDByAdminID(imageID, id);
            return "Image updated successfully";
        }
        adminImages_repository.insertAdminImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
        Long imageID = adminImages_repository.findImageIDByAdminID(id);
        admin_repository.updateImageIDByAdminID(imageID, id);
        return "Image uploaded successfully";
    }


    @Transactional
    @GetMapping(path = { "/admin/image/get/{id}" })
    public AdminImages getAdminImage(@PathVariable("id") Long id) throws IOException {
        if(adminImages_repository.isImageExistByAdminID(id) == 1) {
            final Optional<AdminImages> retrievedImage = adminImages_repository.findByAdminAdminID(id);
            AdminImages img = new AdminImages(retrievedImage.get().getName(), retrievedImage.get().getType(), decompressBytes(retrievedImage.get().getpic_byte()));
            img.setAdmin(retrievedImage.get().getAdmin());
            return img;
        }
        return null;
    }


    @Transactional
    @PostMapping("/user/image/upload/{id}")
    public String uplaodUserImage(@RequestParam("imagefile") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        if(userImages_repository.isImageExistByUserID(id) == 1) {
            userImages_repository.updateUserImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
            Long imageID = userImages_repository.findImageIDByUserID(id);
            user_repository.updateImageIDByUserID(imageID, id);
            return "Image updated successfully";
        }
        userImages_repository.insertUserImage(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()), id);
        Long imageID = userImages_repository.findImageIDByUserID(id);
        user_repository.updateImageIDByUserID(imageID, id);
        return "Image uploaded successfully";
    }


    @Transactional
    @GetMapping(path = { "/user/image/get/{id}" })
    public UserImages getUserImage(@PathVariable("id") Long id) throws IOException {
        if(userImages_repository.isImageExistByUserID(id) == 1) {
            final Optional<UserImages> retrievedImage = userImages_repository.findByUserUserID(id);
            UserImages img = new UserImages(retrievedImage.get().getName(), retrievedImage.get().getType(), decompressBytes(retrievedImage.get().getpic_byte()));
            img.setUser(retrievedImage.get().getUser());
            return img;
        }
        return null;
    }




    // compress the image bytes before storing it in the database
    private static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }


    // uncompress the image bytes before returning it
    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            System.out.println(e);
        }
        return outputStream.toByteArray();
    }

}
