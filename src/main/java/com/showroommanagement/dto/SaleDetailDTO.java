package com.showroommanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaleDetailDTO {
    private String showroomName;
    private String brandName;
    private String productModel;
    private Double productPrice;
    private String employeeName;
    private String departmentName;
    private String branchName;
    private String customerName;
    private String customerAddress;
    private Date salesDate;
}
