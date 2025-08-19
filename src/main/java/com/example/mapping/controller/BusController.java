package com.example.mapping.controller;



import java.time.LocalDate;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mapping.entity.Bus;
import com.example.mapping.entity.Route;
import com.example.mapping.entity.Schedule;
import com.example.mapping.service.BusService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/bus")
@Validated
public class BusController {

    private final BusService service;

    public BusController(BusService service) {
        this.service = service;
    }

    // ---------- BUS ----------
    @PostMapping("/bus")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(service.createBus(bus));
    }

    @GetMapping("/bus")
    public List<Bus> listBuses() { return service.listBuses(); }

    @GetMapping("/bus/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBus(id));
    }

    @PutMapping("/bus/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        return ResponseEntity.ok(service.updateBus(id, bus));
    }

    @DeleteMapping("/bus/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        service.deleteBus(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- ROUTE ----------
    @PostMapping("/route")
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        return ResponseEntity.ok(service.createRoute(route));
    }

    @GetMapping("/route")
    public List<Route> listRoutes() { return service.listRoutes(); }

    @GetMapping("/route/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoute(id));
    }

    @PutMapping("/route/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        return ResponseEntity.ok(service.updateRoute(id, route));
    }

    @DeleteMapping("/route/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        service.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- SCHEDULE ----------
    @PostMapping("/schedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleCreateRequest req) {
    	Schedule schedule = new Schedule();
    	schedule.setTravelDate(req.travelDate());
    	schedule.setDepartureTime(req.departureTime());
    	schedule.setArrivalTime(req.arrivalTime());
    	schedule.setOverridePricePerSeat(req.overridePricePerSeat());

        return ResponseEntity.ok(service.createSchedule(req.busId(), req.routeId(), schedule));
    }

    @GetMapping("/schedule")
    public List<Schedule> listSchedules() { return service.listSchedules(); }
    

    @GetMapping("/schedule/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSchedule(id));
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        service.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- SEARCH ----------
    @GetMapping("/search")
    public List<Schedule> search(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.search(source, destination, date);
    }

    // ---------- Error handler ----------
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(java.util.Map.of("error", ex.getMessage()));
    }

    // ---------- DTO for Schedule creation ----------
    public record ScheduleCreateRequest(
            @NotNull Long busId,
            @NotNull Long routeId,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime departureTime,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime arrivalTime,
            Double overridePricePerSeat
    ) { }
}

