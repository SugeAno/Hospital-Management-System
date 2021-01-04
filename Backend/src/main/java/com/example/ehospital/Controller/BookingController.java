package com.example.ehospital.Controller;

import com.example.ehospital.Model.Booking;
import com.example.ehospital.Model.Schedule;
import com.example.ehospital.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8081"})   // The address is for frontend(vue) running address
//Change the IP if necessary
@RestController
@RequestMapping(path="/Project/REST-API")
public class BookingController {

    @Autowired
    private Booking_repository booking_repository;

    @Autowired
    private Schedule_repository schedule_repository;

    @Autowired
    private Doctor_repository doctor_repository;

    @Autowired
    private User_repository user_repository;


    @Transactional
    @PostMapping("/user/{userid}/addbooking/{doctorid}/{scheduleid}")
    @ResponseBody
    public String addBooking(@PathVariable("userid") long userid, @PathVariable("doctorid") long doctorid, @PathVariable("scheduleid") long scheduleid, @Valid @RequestBody Booking booking) {
        return user_repository.findByUserID(userid).map(user -> {
            booking_repository.insertBooking(
                    booking.getDepartment(),
                    doctorid,
                    scheduleid,
                    booking.getDate(),
                    booking.getTime(),
                    userid,
                    booking.getContact_no(),
                    booking.getEmail());
            return "Booked Successful";
        }).orElseThrow(() -> new ResourceNotFoundException("UserID " + userid + " not found"));
    }


    @Transactional
    @GetMapping(value="/user/{id}/{department}")
    public List<DoctorColumnLimited2> findByDoctorDepartment(@PathVariable("id") Long id, @PathVariable("department") String department) {
        return user_repository.findByUserID(id).map(user -> doctor_repository.findByDepartmentName(department))
                .orElseThrow(() -> new ResourceNotFoundException("UserID " + id + " not found"));
    }

    @Transactional
    @GetMapping(value="/user/{id}/{doctorid}/{date}")
    public List<BookingColumnLimitedForDoctor3> findByDoctorIDAndDate(@PathVariable("id") Long id, @PathVariable("doctorid") Long doctorid, @PathVariable("date") String date) {
        Date conv_date=Date.valueOf(date);
        return user_repository.findByUserID(id).map(user -> booking_repository.findAllByDoctorIDAndDate(doctorid, conv_date))
                .orElseThrow(() -> new ResourceNotFoundException("UserID " + id + " not found"));
    }

    @Transactional
    @GetMapping(value="/user/{id}/bookings")
    public List<BookingColumnLimitedForUser> findAllUserBookings(@PathVariable long id) {
        return user_repository.findByUserID(id).map(user -> booking_repository.findByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("UserID " + id + " not found"));
    }


    @Transactional
    @GetMapping(value="/doctor/{id}/bookings")
    public List<BookingColumnLimitedForDoctor2> findAllDoctorBookings(@PathVariable long id) {
        return doctor_repository.findByDoctorID(id).map(doctor -> booking_repository.findAllByDoctorID(id))
                .orElseThrow(() -> new ResourceNotFoundException("DoctorID " + id + " not found"));
    }


    @Transactional
    @DeleteMapping(value="/user/{userid}/deletebooking/{bookingid}")
    public String deleteUserBooking(@PathVariable("userid") long userid, @PathVariable("bookingid") long bookingid) {
        if(booking_repository.isBookingExistByUserIDAndBookingID(userid, bookingid) == 1) {
            booking_repository.deleteBookingByBookingID(bookingid);
            return "Booking Cancelled Successfully";
        }
        return "This booking does not belongs to this user";
    }


    @Transactional
    @DeleteMapping(value="/doctor/{doctorid}/deletebooking/{bookingid}")
    public String deleteDoctorBooking(@PathVariable("doctorid") long doctorid, @PathVariable("bookingid") long bookingid) {
        if(booking_repository.isBookingExistByDoctorIDAndBookingID(doctorid, bookingid) == 1) {
            booking_repository.deleteBookingByBookingID(bookingid);
            return "Booking Cancelled Successfully";
        }
        return "This booking does not belongs to this doctor";
    }
}
