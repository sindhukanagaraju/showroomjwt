package com.showroommanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Sale_detail")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date salesDate;

    @ManyToOne()
    private Product product;

    @ManyToOne()
    private Customer customer;
}
