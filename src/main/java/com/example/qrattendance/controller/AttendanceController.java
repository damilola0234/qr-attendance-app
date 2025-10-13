package com.example.qrattendance.controller;

import com.example.qrattendance.model.Attendance;
import com.example.qrattendance.model.Staff;
import com.example.qrattendance.repository.AttendanceRepository;
import com.example.qrattendance.repository.StaffRepository;
import com.example.qrattendance.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    @Autowired
    private StaffRepository staffRepo;
    @Autowired private AttendanceRepository attendanceRepo;
    @Autowired private QRService qrService;

    @PostMapping("/staff")
    public Staff registerStaff(@RequestBody Staff staff) throws Exception {
        String qr = qrService.generateQRCode(String.valueOf(staff.getId()));
        Staff savedstaff = staffRepo.save(staff);
        savedstaff.setQrCode(qr);
        return staffRepo.save(savedstaff);
    }

    @PostMapping("/attendance")
    public Attendance logAttendance(@RequestBody Map<String, String> payload) {
        Long personId = Long.parseLong(payload.get("personId"));
        String type = payload.get("type");
        Attendance att = new Attendance();
        att.setPersonId(personId);
        att.setType(type);
        att.setTimestamp(LocalDateTime.now());
        return attendanceRepo.save(att);
    }



    @Controller
    public static class LandingController {

        @GetMapping("/")
        public String welcome() {
            return "forward:/welcome.html";
        }
    }

    @GetMapping("/attendance")
    public List<Attendance> getAttendance() {
        return attendanceRepo.findAll();
    }
}