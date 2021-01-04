package com.example.ehospital.Repository;

import java.sql.Date;
import java.sql.Time;

public interface BookingColumnLimitedForDoctor2 {
    Long getBookingID();
    Long getUserID();
    String getName();
    Date getDate();
    Time getTime();
    Integer getContact_no();
    String getEmail();
}
