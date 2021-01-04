package com.example.ehospital.Model;

import com.fasterxml.jackson.annotation.*;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class.
@Table(name="schedule")
public class Schedule {



    @Id //@Id is used to generate the unique identifier for the objects of persistent class.
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long scheduleID;

    @NotNull
    @Column(nullable = false)
    private Time from_time;

    @NotNull
    @Column(nullable = false)
    private Time to_time;



    @NotNull
    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctorID", referencedColumnName = "doctorID", nullable=false)       //declare the foreign key column
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor doctor;



    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Booking> booking;

    public Schedule() {
        super();
    }

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Time getFrom_time() {
        return from_time;
    }

    public void setFrom_time(Time from_time) {
        this.from_time = from_time;
    }

    public Time getTo_time() {
        return to_time;
    }

    public void setTo_time(Time to_time) {
        this.to_time = to_time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public Set<Booking> getBooking() {
//        return booking;
//    }

    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
        for (Booking book : booking) {
            book.setSchedule(this);
        }
    }
}
