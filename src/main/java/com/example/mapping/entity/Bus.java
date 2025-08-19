package com.example.mapping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "buses")

public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g., "MH12AB1234" or "RedLine Express"
    @Column(nullable = false)
    private String busName;

    // e.g., "AC", "Non-AC", "Sleeper", "Seater"
    @Column(nullable = false)
    private String busType;

    @Column(nullable = false)
    private Integer totalSeats;

    // Default price per seat (can be overridden per schedule if needed)
    @Column(nullable = false)
    private Double pricePerSeat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Double getPricePerSeat() {
		return pricePerSeat;
	}

	public void setPricePerSeat(Double pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

	public Bus(Long id, String busName, String busType, Integer totalSeats, Double pricePerSeat) {
		super();
		this.id = id;
		this.busName = busName;
		this.busType = busType;
		this.totalSeats = totalSeats;
		this.pricePerSeat = pricePerSeat;
	}

	public Bus() {
		super();
	}
}

