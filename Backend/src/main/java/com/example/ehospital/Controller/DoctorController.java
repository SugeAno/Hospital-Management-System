package com.example.ehospital.Controller;

import com.example.ehospital.Model.Doctor;
import com.example.ehospital.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8081"})   // The address is for frontend(vue) running address
                                                                            //Change the IP if necessary
@RestController
@RequestMapping(path="/Project/REST-API")
public class DoctorController {

    @Autowired
    private User_repository user_repository;

    @Autowired
    private Doctor_repository doctor_repository;

    @Autowired
    private Booking_repository booking_repository;

    @Autowired
    private Schedule_repository scheduleRepository;

    @Autowired
    private DoctorImages_repository doctorImages_repository;


    @Transactional
    @GetMapping(value="/doctors")
    public List<DoctorColumnLimited> findAllDoctors() {
        return doctor_repository.findAllDoctors();
    }


    @Transactional
    @PostMapping("/adddoctor")
    @ResponseBody
    public String addDoctors(@Valid @RequestBody Doctor doctor) {
        if(user_repository.isUserExistByUsername(doctor.getUsername()) != 1 && doctor_repository.isDoctorExistByUsername(doctor.getUsername()) != 1) {
            if(user_repository.isUserExistByEmail(doctor.getEmail()) != 1 && doctor_repository.isDoctorExistByEmail(doctor.getEmail()) != 1) {
                doctor_repository.insertDoctor(doctor.getName(),
                        doctor.getUsername(),
                        doctor.getPassword(),
                        doctor.getDepartment(),
                        doctor.getSpecialization(),
                        doctor.getAge(),
                        doctor.getContact_no(),
                        doctor.getEmail(),
                        doctor.getAddress(),
                        false);
                Long id = doctor_repository.findByUsername(doctor.getUsername());
                return Long.toString(id);
            }
            return "Email already exist";
        }
        return "Username already exist";
    }


    @Transactional
    @GetMapping(value="/doctor/departments")
    public List<Object> findAllDoctorDepartments() {
        return doctor_repository.findAllDepartments();
    }


    @Transactional
    @GetMapping(value="/doctor/department/{department}")
    public List<DoctorColumnLimited2> findByDoctorDepartment(@PathVariable("department") String department) {
        return doctor_repository.findByDepartmentName(department);
    }


    @Transactional
    @GetMapping(value="/doctor/{id}")
    public Optional<DoctorColumnLimited> findByDoctorID(@PathVariable long id) {
        return doctor_repository.findByDoctorID(id).map(doctor -> doctor_repository.findByDoctorID(id))
                .orElseThrow(() -> new ResourceNotFoundException("DoctorID " + id + " not found"));
    }


    @Transactional
    @PutMapping("/updatedoctor/{id}")
    public String updateDoctor(@PathVariable long id, @Valid @RequestBody Doctor doctorRequest) {
        if(doctor_repository.isDoctorExistByDoctorID(id) == 1) {
            doctor_repository.updateDoctorDetails(doctorRequest.getName(), doctorRequest.getAge(), doctorRequest.getAddress(), doctorRequest.getContact_no(), doctorRequest.getEmail(), doctorRequest.getDepartment(), doctorRequest.getSpecialization(), id);
            return "Update successful";
        }
        return "User " + id + " not found";
    }


    @Transactional
    @DeleteMapping(value="/deletedoctor/{id}")
    public String deleteDoctor(@PathVariable long id) {
        return doctor_repository.findByDoctorID(id).map(doctor -> {
            booking_repository.deleteAllBookingsByDoctorID(id);
            scheduleRepository.deleteScheduleByDoctorID(id);
            doctorImages_repository.deleteByDoctorID(id);
            doctor_repository.deleteByDoctorID(id);
            return "Doctor Deleted Successfully";
        }).orElseThrow(() -> new ResourceNotFoundException("DoctorID " + id + " not found"));
    }
}
