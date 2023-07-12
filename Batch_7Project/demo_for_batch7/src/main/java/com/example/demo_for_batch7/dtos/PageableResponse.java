package com.example.demo_for_batch7.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<U> {

    private List content;
    public int pageNumber;
    public  int pageSize;
    private  long totalElements;
    private int totalPages;
    private  boolean lastPage;
}
