package com.esi.patientservice.service;

import com.esi.patientservice.model.Patient;
import com.esi.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import com.esi.patientservice.dto.PatientDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    final PatientRepository patientRepository;

    public void addPatientInfo(PatientDto patientDto) {
        var patient = Patient.builder()
            .patientId(patientDto.getPatientId())
            .patientName(patientDto.getPatientName())
            .patientData(patientDto.getPatientData())
            .build();

        patientRepository.save(patient);
    }

}
