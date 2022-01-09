//package com.java.project3.service;
//
//import com.googlecode.jmapper.JMapper;
//import com.java.project3.domain.User;
//import com.java.project3.dto.UserDTO;
//import com.java.project3.dto.base.ResponseDto;
//import com.java.project3.dto.base.SearchReqDto;
//import com.java.project3.repository.UserRepository;
//import lombok.var;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.java.project3.constant.Constants.DEFAULT_PROP;
//import static com.java.project3.utils.SearchUtil.*;
//import static org.springframework.data.domain.Sort.by;
//
//@Service
//public class CustomerUserDetailService implements UserDetailsService {
//    @Autowired
//    UserRepository userRepository;
//
//    JMapper<UserDTO, User> toUserDto;
//    JMapper<User, UserDTO> toUser;
//
//    public CustomerUserDetailService() {
//        this.toUserDto = new JMapper<>(UserDTO.class, User.class);
//        this.toUser = new JMapper<>(User.class, UserDTO.class);
//
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        List<User> listUser = userRepository.findByEmail(email);
//        if (listUser.size() > 0) {
//            User user = listUser.get(0);
//            List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//
//            GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
//            grantList.add(authority);
//
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),grantList);
//
//            return userDetails;
//        } else {
//            new UsernameNotFoundException("Login fail!");
//        }
//        return null;
//    }
//
//
//
//    public ResponseDto search(SearchReqDto reqDto) {
//        ResponseDto responseDto = new ResponseDto();
//        // Dùng hàm search (hero)
//        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
//                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
//        Page<User> users = userRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
//
//        // entity -> dto
//        List<UserDTO> userDTOS = new ArrayList<>();
//        for (var user : users) {
//            userDTOS.add(toUserDto.getDestination(user));
//        }
//
//        // set dto vào object của response
//        responseDto.setObject(prepareResponseForSearch(users.getTotalPages(), users.getNumber(), users.getTotalElements(), userDTOS));
//
//        return responseDto;
//    }
//
//    public ResponseDto signup(UserDTO userDTO){
//        ResponseDto responseDto = new ResponseDto();
//        List<User> user = userRepository.findByEmail(userDTO.getEmail());
//        if (user.size()>0){
//            responseDto.setMessage("Đã có tài khoản này rồi !");
//            responseDto.setObject(null);
//        }
//        else {
//            User user1 = toUser.getDestination(userDTO);
//            responseDto.setObject(userDTO);
//        }
//        return responseDto;
//    }
//}
