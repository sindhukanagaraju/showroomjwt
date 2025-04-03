package com.showroommanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginationDTO {
    private int totalPage;
    private Long totalElement;
    private int pageSize;
    private Object details;
}
