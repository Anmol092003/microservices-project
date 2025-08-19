package com.example.mapping.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mapping.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
	
	
}

