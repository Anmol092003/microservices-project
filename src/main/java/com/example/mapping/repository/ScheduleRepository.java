package com.example.mapping.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mapping.entity.Schedule;

import java.time.LocalDate;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoute_SourceIgnoreCaseAndRoute_DestinationIgnoreCaseAndTravelDate(
            String source, String destination, LocalDate travelDate);
}

