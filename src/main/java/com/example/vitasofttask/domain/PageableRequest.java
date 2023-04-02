package com.example.vitasofttask.domain;
import lombok.Data;

@Data
public class PageableRequest {

    private Integer limit = 5;

    private Integer page = 1;

}
