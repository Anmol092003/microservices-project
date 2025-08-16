package com.example.mapping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "buses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
}

