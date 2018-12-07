package com.ym.investment.assembler;

import com.ym.investment.domain.BaseDomain;
import com.ym.investment.dto.ListDTO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Assembler<T1 extends BaseDomain, T2, T3 extends ListDTO<T4>, T4> {
    public T3 toListDTO(List<T1> source) {
        List<T4> list = source.stream().map(ele ->
                toListDetailsDTO(ele)
        ).collect(Collectors.toList());

        T3 dto = newListDTO();
        dto.setList(list);

        return dto;
    }

    public abstract T4 toListDetailsDTO(T1 source);

    public abstract T2 toDetailsDTO(T1 source);

    public abstract T3 newListDTO();
}
