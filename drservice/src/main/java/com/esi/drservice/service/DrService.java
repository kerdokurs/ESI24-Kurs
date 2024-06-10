package com.esi.drservice.service;

import com.esi.drservice.dto.AppointmentDto;
import com.esi.drservice.model.Appointment;
import com.esi.drservice.repository.DrRepository;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class DrService {
    private final DrRepository drRepository;

    private final WebClient.Builder webClientBuilder;

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

    public List<AppointmentDto> getAllAppointments() {
        var appointments = drRepository.findAll();
        return appointments.stream().map(this::mapToDto).toList();
    }

    private AppointmentDto mapToDto(final Appointment appointment) {
        var patientData = fetchPatientData(appointment.getPatientId());

        return AppointmentDto.builder()
            .appointmentid(appointment.getAppointmentid())
            .patientId(appointment.getPatientId())
            .patientName(appointment.getPatientName())
            .patientData(patientData)
            .drId(appointment.getDrId())
            .date(appointment.getDate())
            .build();
    }

    private String fetchPatientData(final String patientId) {
        var patientData = webClientBuilder.build()
            .get()
            .uri("http://localhost:8083/api/patient-data/{patientId}", patientId)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return patientData;
    }
}
