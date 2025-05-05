package com.showroommanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaginationDTO {
    private int totalPage;
    private Long totalElement;
    private int pageSize;
    private Object details;

    public PaginationDTO(int totalPage, Long totalElement, int pageSize, Object details) {
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.pageSize = pageSize;
        this.details = details;
    }
}
