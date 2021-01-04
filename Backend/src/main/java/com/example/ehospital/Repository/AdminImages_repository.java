package com.example.ehospital.Repository;

import com.example.ehospital.Model.AdminImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdminImages_repository extends JpaRepository<AdminImages,Long> {

    @Transactional
    Optional<AdminImages> findByAdminAdminID(Long adminID);

    @Transactional
    @Query(value = "SELECT id FROM adminimage WHERE adminID = :adminID", nativeQuery = true)
    Long findImageIDByAdminID(@Param("adminID") Long adminID);

    @Transactional
    @Modifying
    @Query(value = "insert into adminimage (name, type, pic_byte, adminID) VALUES (:name, :type, :pic_byte, :adminID)", nativeQuery = true)
    void insertAdminImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("adminID") Long adminID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE adminimage SET name = :name, type = :type, pic_byte = :pic_byte WHERE adminID = :adminID", nativeQuery = true)
    void updateAdminImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("adminID") Long adminID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM adminimage WHERE adminID = :adminID", nativeQuery = true)
    Integer isImageExistByAdminID(@Param("adminID") Long adminID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM adminimage WHERE adminID = :adminID", nativeQuery = true)
    void deleteByAdminID(@Param("adminID") Long adminID);
}
