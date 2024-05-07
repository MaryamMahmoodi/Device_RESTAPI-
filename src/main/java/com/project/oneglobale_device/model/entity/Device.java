package com.project.oneglobale_device.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Table(name ="devices")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Device
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @Column(name = "deleted_at")
    private Timestamp deleted_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;
}
