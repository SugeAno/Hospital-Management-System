package com.example.ehospital.Repository;

import com.example.ehospital.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface Doctor_repository extends JpaRepository<Doctor,Long> {

    @Transactional
    @Query(value = "SELECT doctorID, name, department, specialization, age, contact_no, email, address FROM doctor", nativeQuery = true)
    List<DoctorColumnLimited> findAllDoctors();

    @Transactional
    @Query(value = "SELECT department FROM doctor", nativeQuery = true)
    List<Object> findAllDepartments();

    @Transactional
    @Modifying
    @Query(value = "insert into doctor (name, username, password, department, specialization, age, contact_no, email, address, active) VALUES (:name, :username, :password, :department, :specialization, :age, :contact_no, :email, :address, :active)", nativeQuery = true)
    void insertDoctor(@Param("name") String name, @Param("username") String username, @Param("password") String password,
                      @Param("department") String department, @Param("specialization") String specialization,
                      @Param("age") Integer age, @Param("contact_no") Integer contact_no, @Param("email") String email,
                      @Param("address") String address, @Param("active") Boolean active);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor SET active = :active WHERE doctorID = :doctorID", nativeQuery = true)
    void updateDoctorActiveStatus(@Param("active") Boolean active, @Param("doctorID") Long doctorID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor SET id = :id WHERE doctorID = :doctorID", nativeQuery = true)
    void updateImageIDByDoctorID(@Param("id") Long id, @Param("doctorID") Long doctorID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor SET name = :name, age = :age, address = :address, contact_no = :contact_no, email = :email, department = :department, specialization = :specialization WHERE doctorID = :doctorID", nativeQuery = true)
    void updateDoctorDetails(@Param("name") String name, @Param("age") Integer age, @Param("address") String address, @Param("contact_no") Integer contact_no, @Param("email") String email, @Param("department") String department, @Param("specialization") String specialization, @Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT doctorID, name, department, specialization, age, contact_no, email, address, active FROM doctor WHERE doctorID = :doctorID", nativeQuery = true)
    Optional<DoctorColumnLimited> findByDoctorID(@Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT doctorID, name FROM doctor WHERE department = :department", nativeQuery = true)
    List<DoctorColumnLimited2> findByDepartmentName(@Param("department") String department);

    @Transactional
    @Query(value = "SELECT doctorID FROM doctor WHERE username = :username", nativeQuery = true)
    Long findByUsername(@Param("username") String username);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM doctor WHERE doctorID = :doctorID", nativeQuery = true)
    Integer isDoctorExistByDoctorID(@Param("doctorID") Long doctorID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM doctor WHERE email = :email", nativeQuery = true)
    Integer isDoctorExistByEmail(@Param("email") String email);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM doctor WHERE username = :username", nativeQuery = true)
    Integer isDoctorExistByUsername(@Param("username") String username);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM doctor WHERE username = :username AND password = :password", nativeQuery = true)
    Integer isDoctorExistByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM doctor WHERE doctorID = :doctorID", nativeQuery = true)
    void deleteByDoctorID(@Param("doctorID") Long doctorID);
}
