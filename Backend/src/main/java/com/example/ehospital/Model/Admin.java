package com.example.ehospital.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity                     // This tells Hibernate to make a table out of this class.
@Table(name="admin")
public class Admin {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long adminID;

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

    @Column(nullable = false)
    private Boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", referencedColumnName = "id")       //declare the foreign key column
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AdminImages adminImages;

    public Long getAdminID() {
        return adminID;
    }

    public void setAdminID(Long adminID) {
        this.adminID = adminID;
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
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

//    public AdminImages getAdminImages() {
//        return adminImages;
//    }

    public void setAdminImages(AdminImages adminImages) {
        this.adminImages = adminImages;
    }
}
