package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Course;
import com.java.project3.domain.User;
import com.java.project3.dto.CourseDTO;
import com.java.project3.dto.UserDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.repository.UserRepository;
import com.java.project3.service.base.GenIdService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @Autowired
    GenIdService genIdService;

    JMapper<UserDTO, User> toUserDto;
    JMapper<User, UserDTO> toUser;


    public UserService() {
        this.toUserDto = new JMapper<>(UserDTO.class, User.class);
        this.toUser = new JMapper<>(User.class, UserDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = toUserDto.getDestination(user.get());
            responseDto.setObject(userDTO);
        }
        return responseDto;
    }

    public ResponseDto update(UserDTO userDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<User> user = userRepository.findById(userDTO.getId());
        if (user.isPresent()) {
            User user1 = toUser.getDestination(user.get(), userDTO);
            User result = userRepository.save(user1);
            UserDTO userDTO1 = toUserDto.getDestination(result);
            responseDto.setObject(userDTO1);
        }
        return responseDto;
    }
}
