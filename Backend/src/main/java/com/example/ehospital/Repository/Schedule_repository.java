package com.example.ehospital.Repository;

import com.example.ehospital.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface Schedule_repository extends JpaRepository<Schedule,Long> {

    Optional<Schedule> findByScheduleID(Long scheduleID);
    List<Schedule> findAll();

    @Transactional
    @Query(value = "SELECT * FROM schedule WHERE doctorID = :doctorID", nativeQuery = true)
    List<Schedule> findByDoctorID(Long doctorID);

    List<Schedule> findByDoctorDoctorID(Long doctorID);

    Optional<Schedule> findByScheduleIDAndDoctorDoctorID(Long scheduleID, Long doctorID);

    Schedule findByDateAndDoctorDoctorID(Date date, Long doctorID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM schedule WHERE doctorID = :doctorID AND scheduleID = :scheduleID", nativeQuery = true)
    Integer isScheduleExistByDoctorIDAndScheduleID(@Param("doctorID") Long doctorID, @Param("scheduleID") Long scheduleID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM schedule WHERE doctorID = :doctorID", nativeQuery = true)
    void deleteScheduleByDoctorID(@Param("doctorID") Long doctorID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM schedule WHERE scheduleID = :scheduleID", nativeQuery = true)
    void deleteScheduleByScheduleID(@Param("scheduleID") Long scheduleID);
}
