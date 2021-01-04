package com.example.ehospital.Repository;

import com.example.ehospital.Model.UserImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserImages_repository extends JpaRepository<UserImages,Long> {

    @Transactional
    Optional<UserImages> findByUserUserID(Long userID);

    @Transactional
    @Query(value = "SELECT id FROM userimage WHERE userID = :userID", nativeQuery = true)
    Long findImageIDByUserID(@Param("userID") Long userID);

    @Transactional
    @Modifying
    @Query(value = "insert into userimage (name, type, pic_byte, userID) VALUES (:name, :type, :pic_byte, :userID)", nativeQuery = true)
    void insertUserImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("userID") Long userID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE userimage SET name = :name, type = :type, pic_byte = :pic_byte WHERE userID = :userID", nativeQuery = true)
    void updateUserImage(@Param("name") String name, @Param("type") String type, @Param("pic_byte") byte[] pic_byte, @Param("userID") Long userID);

    @Transactional
    @Query(value = "SELECT COUNT(1) FROM userimage WHERE userID = :userID", nativeQuery = true)
    Integer isImageExistByUserID(@Param("userID") Long userID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM userimage WHERE userID = :userID", nativeQuery = true)
    void deleteByUserID(@Param("userID") Long userID);
}
