package com.example.qrattendance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;
    private Long personId;
    private String type;
    private LocalDateTime timestamp;

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setTimestamp(LocalDateTime now) {
    }

    public void setType(String type) {
    }
    // Getters and setters
}