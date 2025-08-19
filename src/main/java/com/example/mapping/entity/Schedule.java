package com.example.mapping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(nullable = false)
    private LocalDate travelDate;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    // Optional: allow override of Bus.pricePerSeat for special days
    private Double overridePricePerSeat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Double getOverridePricePerSeat() {
		return overridePricePerSeat;
	}

	public void setOverridePricePerSeat(Double overridePricePerSeat) {
		this.overridePricePerSeat = overridePricePerSeat;
	}

	public Schedule(Long id, Bus bus, Route route, LocalDate travelDate, LocalDateTime departureTime,
			LocalDateTime arrivalTime, Double overridePricePerSeat) {
		super();
		this.id = id;
		this.bus = bus;
		this.route = route;
		this.travelDate = travelDate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.overridePricePerSeat = overridePricePerSeat;
	}

	public Schedule() {
		super();
	}
    
    
}
