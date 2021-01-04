package com.example.ehospital.Repository;

import com.example.ehospital.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

@Repository
public interface Booking_repository  extends JpaRepository<Booking,Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into booking (department, doctorID, scheduleID, date, time, userID, contact_no, email) VALUES (:department, :doctorID, :scheduleID, :date, :time, :userID, :contact_no, :email)", nativeQuery = true)
    void insertBooking(@Param("department") String department, @Param("doctorID") Long doctorID, @Param("scheduleID") Long scheduleID,
                       @Param("date") Date date, @Param("time") Time time, @Param("userID") Long userID, @Param("contact_no") Integer contact_no, @Param("email") String email);

    @Transactional
    @Query(value = "SELECT b.bookingID, b.department, b.doctorID, b.date, b.time, b.contact_no, b.email, d.name FROM booking b, doctor d WHERE b.userID = :userID AND b.doctorID = d.doctorID", nativeQuery = true)
    List<BookingColumnLimitedForUser> findByUserID(@Param("userID") Long userID);

    @Transactional
    @Query(value = "SELECT b.bookingID, b.userID, b.date, b.time, b.contact_no, b.email, u.name FROM booking b, user u WHERE b.doctorID = :doctorID AND b.userID = u.userID", nativeQuery = true)
    List<BookingColumnLimitedForDoctor2> findAllByDoctorID(@Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT b.bookingID, b.date, b.time FROM booking b WHERE b.doctorID = :doctorID AND b.date = :date", nativeQuery = true)
    List<BookingColumnLimitedForDoctor3> findAllByDoctorIDAndDate(@Param("doctorID") Long doctorID, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM booking WHERE userID = :userID AND bookingID = :bookingID", nativeQuery = true)
    Integer isBookingExistByUserIDAndBookingID(@Param("userID") Long userID, @Param("bookingID") Long bookingID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM booking WHERE doctorID = :doctorID AND bookingID = :bookingID", nativeQuery = true)
    Integer isBookingExistByDoctorIDAndBookingID(@Param("doctorID") Long doctorID, @Param("bookingID") Long bookingID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM booking WHERE userID = :userID", nativeQuery = true)
    void deleteAllBookingsByUserID(@Param("userID") Long userID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM booking WHERE doctorID = :doctorID", nativeQuery = true)
    void deleteAllBookingsByDoctorID(@Param("doctorID") Long doctorID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM booking WHERE doctorID = :doctorID AND scheduleID = :scheduleID", nativeQuery = true)
    void deleteBookingByScheduleIDAndDoctorID(@Param("doctorID") Long doctorID, @Param("scheduleID") Long scheduleID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM booking WHERE bookingID = :bookingID", nativeQuery = true)
    void deleteBookingByBookingID(@Param("bookingID") Long bookingID);
}
