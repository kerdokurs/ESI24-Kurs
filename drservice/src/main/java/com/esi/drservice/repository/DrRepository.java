package com.esi.drservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.esi.drservice.model.Appointment;

public interface DrRepository extends JpaRepository<Appointment, String> {
    @Override
    List<Appointment> findAll();
}
