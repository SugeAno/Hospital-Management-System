package com.example.ehospital.Repository;

import com.example.ehospital.Model.DoctorImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DoctorImages_repository extends JpaRepository<DoctorImages,Long> {

    @Transactional
    Optional<DoctorImages> findByDoctorDoctorID(Long doctorID);

    @Transactional
    @Query(value = "SELECT * FROM doctorimage WHERE doctorid in (SELECT doctorid FROM doctor WHERE department = :department)", nativeQuery = true)
    List<DoctorImages> findByDoctorDepartment(String department);

    @Transactional
    @Query(value = "SELECT id FROM doctorimage WHERE doctorID = :doctorID", nativeQuery = true)
    Long findImageIDByDoctorID(@Param("doctorID") Long doctorID);

    @Transactional
    @Modifying
    @Query(value = "insert into doctorimage (name, type, pic_byte, doctorID) VALUES (:name, :type, :pic_byte, :doctorID)", nativeQuery = true)
    void insertDoctorImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("doctorID") Long doctorID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctorimage SET name = :name, type = :type, pic_byte = :pic_byte WHERE doctorID = :doctorID", nativeQuery = true)
    void updateDoctorImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM doctorimage WHERE doctorID = :doctorID", nativeQuery = true)
    Integer isImageExistByDoctorID(@Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT Count(*) FROM doctorimage WHERE doctorid in (SELECT doctorid FROM doctor WHERE department = :department)", nativeQuery = true)
    Integer isImageExistByDepartment(@Param("department") String department);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM doctorimage WHERE doctorID = :doctorID", nativeQuery = true)
    void deleteByDoctorID(@Param("doctorID") Long doctorID);
}
