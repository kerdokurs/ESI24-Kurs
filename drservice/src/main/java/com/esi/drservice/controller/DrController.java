package com.esi.drservice.controller;

import com.esi.drservice.dto.AppointmentDto;
import com.esi.drservice.service.DrService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DrController {
    private final DrService drService;

    @Autowired
    DrController(final DrService drService) {
        this.drService = drService;
    }

     @GetMapping("/appointment")
     public List<AppointmentDto> getAllAppointments() {
        return drService.getAllAppointments();
     }

}
