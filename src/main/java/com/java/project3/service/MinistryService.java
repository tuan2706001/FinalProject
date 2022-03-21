package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.User;
import com.java.project3.dto.UserDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.UserRepository;
import com.java.project3.service.base.GenIdService;
import com.java.project3.utils.PageUltil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.java.project3.constant.Constants.DEFAULT_PROP;
import static com.java.project3.utils.SearchUtil.*;
import static org.springframework.data.domain.Sort.by;

@Service
public class MinistryService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MinistryService ministryService;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    JMapper<UserDTO, User> toUserDto;
    JMapper<User, UserDTO> toUser;


    public MinistryService() {
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

    public ResponseDto create(UserDTO userDTO) {
        ResponseDto responseDto = new ResponseDto();
        Long email = userRepository.countEmail(userDTO.getEmail());
        if (email == 0) {
            User user = toUser.getDestination(userDTO);
            user.setId(genIdService.nextId());
            user.setPassword(userDTO.getPassword());
            User result = userRepository.save(user);
            var temp = toUserDto.getDestination(result);
            responseDto.setObject(temp);
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

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<User> users = userRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<UserDTO> userDTOS = new ArrayList<>();
        for (var user : users) {
            if (user.getStatus() != null) {
                UserDTO userDTO = toUserDto.getDestination(user);
                userDTOS.add(userDTO);
            }
        }
        responseDto.setObject(prepareResponseForSearch(users.getTotalPages(), users.getNumber(), users.getTotalElements(), userDTOS));
        return responseDto;
    }

    public ResponseDto searchMajorBy(Integer pageIndex, Integer pageSize, String search) {
        ResponseDto responseDto = new ResponseDto();
        SearchReqDto searchReqDto = new SearchReqDto();
        com.java.project3.dto.base.Page
                page = PageUltil.setDefault(pageIndex, pageSize);
        searchReqDto.setPageIndex(page.getCurrentPage() - 1);
        searchReqDto.setPageSize(page.getPageSize());
        List<String> sort = new ArrayList<>();
        sort.add("id");
        searchReqDto.setSorts(sort);
        String sql = "";
        if (search != null) {
            sql = "S-fullName=L\"" + search + "\", S-email=L\"" + search + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = ministryService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

}
