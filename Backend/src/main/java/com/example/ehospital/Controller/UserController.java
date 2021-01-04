package com.example.ehospital.Controller;

import com.example.ehospital.Repository.*;
import com.example.ehospital.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8081"})   // The address is for frontend(vue) running address

@RestController
@RequestMapping(path="/Project/REST-API")
public class UserController {

    @Autowired
    private User_repository user_repository;

    @Autowired
    private Booking_repository booking_repository;

    @Autowired
    private Doctor_repository doctor_repository;

    @Autowired
    private Admin_repository admin_repository;

    @Autowired
    private UserImages_repository userImages_repository;


    @Transactional
    @GetMapping(value="/users")
    public List<UserColumnLimited> findAllUsers() {
        return user_repository.findAllUsers();
    }


    @Transactional
    @GetMapping(value="/checkusername", params = { "username"})
    @ResponseBody
    public String checkUsername(@RequestParam("username") String username) {
        if(admin_repository.isAdminExistByUsername(username) == 1 || user_repository.isUserExistByUsername(username) == 1 || doctor_repository.isDoctorExistByUsername(username) == 1) {
            return "Username already Exist";
        }
        return "Username does not Exist";
    }


    @Transactional
    @PostMapping("/adduser")
    @ResponseBody
    public String addUser(@Valid @RequestBody User user) {
        if(admin_repository.isAdminExistByUsername(user.getUsername()) != 1 && user_repository.isUserExistByUsername(user.getUsername()) != 1 && doctor_repository.isDoctorExistByUsername(user.getUsername()) != 1) {
            if(admin_repository.isAdminExistByEmail(user.getEmail()) != 1 && user_repository.isUserExistByEmail(user.getEmail()) != 1 && doctor_repository.isDoctorExistByEmail(user.getEmail()) != 1) {
                user_repository.insertUser(user.getName(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getAge(),
                        user.getAddress(),
                        user.getContact_no(),
                        user.getEmail(),
                        false);
                return "User account created Successfully";
            }
            return "Email already exist";
        }
        return "Username already exist";
    }


    @Transactional
    @GetMapping(value="/user/{id}")
    public Optional<UserColumnLimited> findByUserID(@PathVariable long id) {
        return user_repository.findByUserID(id).map(user -> user_repository.findByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("UserID " + id + " not found"));
    }


    @Transactional
    @PutMapping("/updateuser/{id}")
    public String updateUser(@PathVariable long id, @Valid @RequestBody User userRequest) {
        if(user_repository.isUserExistByUserID(id) == 1) {
            user_repository.updateUserDetails(userRequest.getName(), userRequest.getAge(), userRequest.getAddress(), userRequest.getContact_no(), userRequest.getEmail(), id);
            return "Update successful";
        }
        return "User " + id + " not found";
    }


    @Transactional
    @DeleteMapping(value="/deleteuser/{id}")
    public String deleteUser(@PathVariable long id) {
        return user_repository.findByUserID(id).map(user -> {
            booking_repository.deleteAllBookingsByUserID(id);
            userImages_repository.deleteByUserID(id);
            user_repository.deleteByUserID(id);
            return "User Deleted Successfully";
        }).orElseThrow(() -> new ResourceNotFoundException("UserID " + id + " not found"));
    }
}
