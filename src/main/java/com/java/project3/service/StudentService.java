package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.*;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseClassRepository;
import com.java.project3.repository.StudentRepository;
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
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseClassRepository courseClassRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    MarkRepository markRepository;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    JMapper<StudentDTO, Student> toStudentDto;
    JMapper<Student, StudentDTO> toStudent;
    JMapper<UserDTO, User> toUserDto;
    JMapper<User, UserDTO> toUser;
    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public StudentService() {
        this.toStudentDto = new JMapper<>(StudentDTO.class, Student.class);
        this.toStudent = new JMapper<>(Student.class, StudentDTO.class);
        this.toUserDto = new JMapper<>(UserDTO.class, User.class);
        this.toUser = new JMapper<>(User.class, UserDTO.class);
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            StudentDTO studentDTO = toStudentDto.getDestination(student.get());
            CourseClass courseClass = courseClassRepository.findById(studentDTO.getCourseClassId()).orElse(null);
            studentDTO.setCourseClassName(courseClass.getName());
            responseDto.setObject(studentDTO);
        }
        return responseDto;
    }

    public ResponseDto findByCourseClassId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Student> students = studentRepository.findByCourseClassId(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (var item : students) {
            studentDTOS.add(toStudentDto.getDestination(item));
        }
        responseDto.setObject(studentDTOS);
        return responseDto;
    }

    public ResponseDto findByCscId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Student> students = studentRepository.findByCtdtSubjectClassId(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (var item : students) {
            studentDTOS.add(toStudentDto.getDestination(item));
        }
        responseDto.setObject(studentDTOS);
        return responseDto;
    }

    public ResponseDto findByCtdtId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Student> students = studentRepository.findByCtdtId(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (var item : students) {
            studentDTOS.add(toStudentDto.getDestination(item));
        }
        responseDto.setObject(studentDTOS);
        return responseDto;
    }

    public ResponseDto create(StudentDTO studentDTO) {
        ResponseDto responseDto = new ResponseDto();
//        Optional<CourseClass> courseClass = courseClassRepository.findById(studentDTO.getCourseClassId());
        Long checkEmail = studentRepository.countStudentByEmail(studentDTO.getEmail());
        if (checkEmail == 0) {
        Student student = toStudent.getDestination(studentDTO);
        student.setId(genIdService.nextId());
//        student.setCourseClassName(courseClass.get().getName());
        student.setPassword("123456");
        Student result = studentRepository.save(student);
        var temp = toStudentDto.getDestination(result);
        }
        return responseDto;
//        // tạo bản ghi bảng user
//        UserDTO userDTO = new UserDTO();
//        User user = toUser.getDestination(userDTO);
//        user.setId(genIdService.nextId());
//        user.setFullName(studentDTO.getFullName());
//        user.setEmail(studentDTO.getEmail());
//        user.setPhone(studentDTO.getPhone());
//        user.setPassword("123456");
//        user.setGender(studentDTO.getGender());
//        user.setBirthday(studentDTO.getBirthday());
//        user.setAddress(studentDTO.getAddress());
//        user.setRole(Enum.SINH_VIEN.value);
//        User result1 = userRepository.save(user);
//        var temp1 = toUserDto.getDestination(result1);

    }

    public ResponseDto update(StudentDTO studentDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Student> student = studentRepository.findById(studentDTO.getId());
        if (student.isPresent()) {
//            CourseClass courseClass = courseClassRepository.findById(studentDTO.getCourseClassId()).get();

            Student student1 = toStudent.getDestination(student.get(), studentDTO);
//            student1.setCourseClassName(courseClass.getName());
            Student result = studentRepository.save(student1);
            StudentDTO studentDTO1 = toStudentDto.getDestination(result);
            responseDto.setObject(studentDTO1);

//            //update lại tên sinh viên trong điểm
//            List<Mark> marks = markRepository.findByStudentId(studentDTO.getId());
//            List<MarkDTO> markDTOS = new ArrayList<>();
//            for (var item : marks) {
//                markDTOS.add(toMarkDto.getDestination(item));
//                item.setStudentName(student1.getFullName());
//                markRepository.save(item);
//            }
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Student> students = studentRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (var student : students) {
            StudentDTO studentDTO = toStudentDto.getDestination(student);
            CourseClass courseClass = courseClassRepository.findById(studentDTO.getCourseClassId()).orElse(null);
            studentDTO.setCourseClassName(courseClass.getName());
            studentDTOS.add(studentDTO);
        }
        responseDto.setObject(prepareResponseForSearch(students.getTotalPages(), students.getNumber(), students.getTotalElements(), studentDTOS));
        return responseDto;
    }

    public ResponseDto searchStudentBy(Integer pageIndex, Integer pageSize, String search, Long gradeId) {
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
            sql += "S-fullName=L\"" + search + "\", OR-S-studentCode=L\"" + search + "\"";
        }
        if (gradeId != null) {
            sql += ",N-gradeId=\"" + gradeId + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = studentService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long student = studentRepository.count();
        return student;
    }
}
