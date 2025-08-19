package com.example.mapping.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mapping.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> { }

