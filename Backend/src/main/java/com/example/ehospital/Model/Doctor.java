package com.example.ehospital.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity                     // This tells Hibernate to make a table out of this class.
@Table(name="doctor", indexes = {
        @Index(name = "DEPT_INDX_1", columnList = "department"),
        @Index(name = "USERNAME_INDX_1", columnList = "username"),
        @Index(name = "EMAIL_INDX_1", columnList = "email")})
public class Doctor {

    @Id                     //@Id is used to generate the unique identifier for the objects of persistent class.
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long doctorID;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false)
    private String username;

    @NotNull
    @Size(max = 15)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String department;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private Integer age;

    private Integer contact_no;@OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Schedule> schedule;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String email;

    @Size(max = 100)
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    @Column(nullable = false)
    private Boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", referencedColumnName = "id")       //declare the foreign key column
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DoctorImages doctorImages;



    public Doctor() {
        super();
    }

    public Doctor(String name, String specialization, Integer contact_no, String email, String address) {
        this.name = name;
        this.specialization = specialization;
        this.contact_no = contact_no;
        this.email = email;
        this.address = address;
    }


    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getContact_no() {
        return contact_no;
    }

    public void setContact_no(Integer contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
//        this.address.setDoctor(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    public Set<Booking> getBookings() {
//        return bookings;
//    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
        for (Booking book : bookings) {
            book.setDoctor(this);
        }
    }

//    public Set<Schedule> getSchedule() {
//        return schedule;
//    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
        for (Schedule sh : schedule) {
            sh.setDoctor(this);
        }
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

//    public DoctorImages getDoctorImages() {
//        return doctorImages;
//    }

    public void setDoctorImages(DoctorImages doctorImages) {
        this.doctorImages = doctorImages;
    }
}
