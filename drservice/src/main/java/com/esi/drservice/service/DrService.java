package com.esi.drservice.service;

import com.esi.drservice.dto.AppointmentDto;
import com.esi.drservice.model.Appointment;
import com.esi.drservice.repository.DrRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DrService {
    final DrRepository drRepository;

    @KafkaListener(
        topics = "appointmentTopic",
        groupId = "apt-topic-listener-1"
    )
    public void onAppointmentRecevied(final AppointmentDto appointmentDto) {
        var appointment = Appointment.builder()
            .appointmentid(appointmentDto.getAppointmentid())
            .patientId(appointmentDto.getPatientId())
            .patientName(appointmentDto.getPatientName())
            .drId(appointmentDto.getDrId())
            .date(appointmentDto.getDate())
            .build();

        drRepository.save(appointment);
    }
}
