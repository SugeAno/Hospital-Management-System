package com.example.ehospital.Repository;

import java.sql.Date;
import java.sql.Time;

public interface BookingColumnLimitedForDoctor {
    Long getBookingID();
    Long getUserID();
    Date getDate();
    Time getTime();
    Integer getContact_no();
    String getEmail();
}
