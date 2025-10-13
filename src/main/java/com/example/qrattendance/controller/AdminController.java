package com.example.qrattendance.controller;

import com.example.qrattendance.model.Staff;
import com.example.qrattendance.model.Attendance;
import com.example.qrattendance.repository.StaffRepository;
import com.example.qrattendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private StaffRepository staffRepo;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        List<Staff> staffList = staffRepo.findAll();
        List<Attendance> attendanceList = attendanceRepo.findAll();

        model.addAttribute("staffList", staffList);
        model.addAttribute("attendanceList", attendanceList);

        return "admin";
    }
}
