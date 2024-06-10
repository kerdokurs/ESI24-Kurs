package com.esi.patientservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esi.patientservice.dto.PatientDto;
import com.esi.patientservice.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/patient")
    public ResponseEntity<String> addPatientInfo(@RequestBody PatientDto patientDto) {
        patientService.addPatientInfo(patientDto);
        return ResponseEntity.ok("Patient info recieved");
    }

    @GetMapping("/patient-data/{patientId}")
    public ResponseEntity<String> getPatientData(@PathVariable String patientId) {
        var patientData = patientService.getPatientData(patientId)
            .orElseThrow();
        return ResponseEntity.ok(patientData);
    }

}
