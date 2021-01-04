package com.example.ehospital.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name = "doctorimage")
public class DoctorImages {

    public DoctorImages() {
        super();
    }

    public DoctorImages(String name, String type, byte[] pic_byte) {
        this.name = name;
        this.type = type;
        this.pic_byte = pic_byte;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "type")
    private String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "pic_byte", length = 1000)
    private byte[] pic_byte;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID", referencedColumnName = "doctorID")       //declare the foreign key column
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor doctor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getpic_byte() {
        return pic_byte;
    }

    public void setpic_byte(byte[] pic_byte) {
        this.pic_byte = pic_byte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

