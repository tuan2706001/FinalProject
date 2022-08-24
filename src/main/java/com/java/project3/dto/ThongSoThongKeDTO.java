package com.java.project3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThongSoThongKeDTO {

    private List<MarkDTO> markDTOS;

    private Integer sumQuaMon =0;

    private Integer sumThiLai=0;

    private Integer sumHocLai=0;
}
