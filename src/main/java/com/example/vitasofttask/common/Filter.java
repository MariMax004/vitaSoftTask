package com.example.vitasofttask.common;


import com.example.vitasofttask.domain.PageableRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(callSuper = true)
public class Filter extends PageableRequest {
    private Sort sort;

}

