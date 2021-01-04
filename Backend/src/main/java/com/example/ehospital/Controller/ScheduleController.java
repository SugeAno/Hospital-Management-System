package com.example.ehospital.Controller;

import com.example.ehospital.Model.Schedule;
import com.example.ehospital.Repository.Booking_repository;
import com.example.ehospital.Repository.Doctor_repository;
import com.example.ehospital.Repository.Schedule_repository;
import com.example.ehospital.Repository.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:8081", "http://192.168.1.102:8081"})   // The address is for frontend(vue) running address

@RestController
@RequestMapping(path="/Project/REST-API")
public class ScheduleController {

    @Autowired
    private Schedule_repository scheduleRepository;

    @Autowired
    private Booking_repository booking_repository;

    @Autowired
    private Doctor_repository doctorRepository;

    @Autowired
    private User_repository user_repository;


//    @Transactional
//    @GetMapping(value="/doctors/{id}/schedules")
//    public List<Schedule> getAllSchedulesByDoctorID(@PathVariable (value = "id") long id) {
//        return scheduleRepository.findByDoctorDoctorID(id);
//    }


    @Transactional
    @GetMapping(value="/doctor/{id}/schedules")
    public List<Schedule> findAllDoctorSchedules(@PathVariable long id) {
        return doctorRepository.findByDoctorID(id).map(doctor -> scheduleRepository.findByDoctorID(id))
                .orElseThrow(() -> new ResourceNotFoundException("DoctorID " + id + " not found"));
    }





//    @PostMapping("/doctors/{id}/addschedules")
//    @ResponseBody
//    public Schedule addSchedules(@PathVariable (value = "id") long id, @Valid @RequestBody Schedule schedule) {
//        return doctorRepository.findByDoctorID(id).map(doctor -> {
//            schedule.setDoctor(doctor);
//
//            return scheduleRepository.save(schedule);
//        }).orElseThrow(() -> new ResourceNotFoundException("DoctorID " + id + " not found"));
//    }


    @Transactional
    @PutMapping("/doctors/{id}/updateschedules/{scheduleid}")
    public Schedule updateSchedule(@PathVariable (value = "id") long doctorid, @PathVariable (value = "scheduleid") long scheduleid,@Valid @RequestBody Schedule scheduleRequest) {
        if (doctorRepository.findByDoctorID(doctorid).isPresent()) {
            return scheduleRepository.findByScheduleID(scheduleid).map(schedule -> {
                schedule.setFrom_time(scheduleRequest.getFrom_time());
                schedule.setTo_time(scheduleRequest.getTo_time());

                return scheduleRepository.save(schedule);
            }).orElseThrow(() -> new ResourceNotFoundException("ScheduleID " + scheduleid + " not found"));
        }
        throw new ResourceNotFoundException("DoctorID " + doctorid + " not found");

    }


    @Transactional
    @DeleteMapping(value="/doctors/{id}/deleteschedules/{scheduleid}")
    public String deleteSchedule(@PathVariable (value = "id") long doctorid, @PathVariable (value = "scheduleid") long scheduleid) {
        if(scheduleRepository.isScheduleExistByDoctorIDAndScheduleID(doctorid, scheduleid) == 1) {
            booking_repository.deleteBookingByScheduleIDAndDoctorID(doctorid, scheduleid);
            scheduleRepository.deleteScheduleByScheduleID(scheduleid);
            return "Schedule Deleted Successfully";
        }
        return "This schedule does not belongs to this doctor";
    }

}
