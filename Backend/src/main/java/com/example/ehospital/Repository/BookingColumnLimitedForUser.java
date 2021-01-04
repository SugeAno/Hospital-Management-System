package com.example.ehospital.Repository;

import com.example.ehospital.Model.Booking;
import com.example.ehospital.Model.Doctor;

import java.sql.Date;
import java.sql.Time;

public interface BookingColumnLimitedForUser {
    Long getBookingID();
    String getDepartment();
    Long getDoctorID();
    String getName();
    Date getDate();
    Time getTime();
    Integer getContact_no();
    String getEmail();
}
