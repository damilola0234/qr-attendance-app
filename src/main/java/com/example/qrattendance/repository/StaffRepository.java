package com.example.qrattendance.repository;

import com.example.qrattendance.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {}