//package com.java.project3.exception;
//
//import com.java.project3.dto.base.ResponseDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.multipart.MultipartException;
//
//import java.nio.file.AccessDeniedException;
//
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    //StandardServletMultipartResolver
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<String> handleErrorUploadFile(MultipartException e) {
//        return new ResponseEntity<>("Loi", HttpStatus.OK);
//    }
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ResponseDto> handleErrorLogin() {
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage("Tài khoản này không tồn tại hoặc chưa được kích hoạt!");
//        responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
//        responseDto.setObject(null);
//        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ResponseDto> handleAccessDeniedException() {
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage("Truy cập bị từ chối!");
//        responseDto.setStatus(HttpStatus.FORBIDDEN.value());
//        responseDto.setObject(null);
//        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
//    }
//
//}
