package com.java.project3.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private Integer status = HttpStatus.OK.value();

    private String message = HttpStatus.OK.getReasonPhrase();

    private Object object;

    public ResponseDto(Object object) {
        this.object = object;
    }
}
