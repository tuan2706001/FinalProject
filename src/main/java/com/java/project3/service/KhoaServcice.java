package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Coursse;
import com.java.project3.dto.CourseDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.KhoaRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.java.project3.utils.SearchUtil.getOrders;
import static org.springframework.data.domain.Sort.by;

@Service
public class KhoaServcice {
    @Autowired
    KhoaRepository khoaRepository;


    JMapper<CourseDTO, Coursse> toKhoaDto;
    JMapper<Coursse, CourseDTO> toKhoa;


    public KhoaServcice() {
        this.toKhoaDto = new JMapper<>(CourseDTO.class, Coursse.class);
        this.toKhoa = new JMapper<>(Coursse.class, CourseDTO.class);
    }


    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Coursse> khoas = khoaRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (var khoa : khoas) {
            courseDTOS.add(toKhoaDto.getDestination(khoa));
        }
        responseDto.setObject(prepareResponseForSearch(khoas.getTotalPages(), khoas.getNumber(), khoas.getTotalElements(), courseDTOS));
        return responseDto;
    }


}
