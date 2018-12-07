package com.ym.investment.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListDTO<T1> {
    private List<T1> list;
}
