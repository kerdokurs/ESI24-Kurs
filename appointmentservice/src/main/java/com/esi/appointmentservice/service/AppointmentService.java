package com.esi.appointmentservice.service;

import com.esi.appointmentservice.dto.AppointmentDto;
import com.esi.appointmentservice.model.Appointment;
import com.esi.appointmentservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final KafkaTemplate<String, AppointmentDto> aptKafkaTemplate;

    public void appointmentBooked(AppointmentDto appointmentDto) {
        var appointment = Appointment.builder()
            .appointmentid(appointmentDto.getAppointmentid())
            .patientId(appointmentDto.getPatientId())
            .patientName(appointmentDto.getPatientName())
            .drId(appointmentDto.getDrId())
            .date(appointmentDto.getDate())
            .build();

        appointmentRepository.save(appointment);
        aptKafkaTemplate.send("appointmentTopic", appointmentDto);
    }

}
