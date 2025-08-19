package com.example.mapping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routes")

public class Route {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    private Double distanceKm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(Double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public Route() {
		super();
	}

	public Route(Long id, String source, String destination, Double distanceKm) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.distanceKm = distanceKm;
	}
    
    
}
