package com.showroommanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "Address")
    private String address;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Branch branch;
}
