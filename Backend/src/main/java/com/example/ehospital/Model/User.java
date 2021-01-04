package com.example.ehospital.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity                     // This tells Hibernate to make a table out of this class.
@Table(name="user", indexes = {
        @Index(name = "USERNAME_INDX_2", columnList = "username"),
        @Index(name = "EMAIL_INDX_2", columnList = "email")})
public class User {
    @Id                     //@Id is used to generate the unique identifier for the objects of persistent class.
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userID;

    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @Size(max = 20)
    @Column(nullable = false)
    private String username;

    @Size(max = 15)
    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
    private Integer age;

    private Integer contact_no;

    @Size(max = 50)
    @Column(nullable = false)
    private String email;

    @Size(max = 100)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    @Column(nullable = false)
    private Boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", referencedColumnName = "id")       //declare the foreign key column
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserImages userImages;


    public User() {
        super();
    }

    public User(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
//        this.address.setUser(this);
    }

//    public Set<Booking> getBookings() {
//        return bookings;
//    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
        for (Booking book : bookings) {
            book.setUser(this);
        }
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

//    public UserImages getUserImages() {
//        return userImages;
//    }

    public void setUserImages(UserImages userImages) {
        this.userImages = userImages;
    }
}
