package com.example.ehospital.Repository;

public interface DoctorColumnLimited {
    Long getDoctorID();
    String getName();
    String getDepartment();
    String getSpecialization();
    Integer getAge();
    Integer getContact_no();
    String getEmail();
    String getAddress();
    Boolean getActive();
}
