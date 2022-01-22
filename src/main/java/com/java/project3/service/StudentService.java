package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Grade;
import com.java.project3.domain.Student;
import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.GradeRepository;
import com.java.project3.repository.StudentRepository;
import com.java.project3.service.base.GenIdService;
import com.java.project3.utils.PageUltil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    GradeRepository gradeRepository;
    @Autowired
    GenIdService genIdService;

    JMapper<StudentDTO, Student> toStudentDto;
    JMapper<Student, StudentDTO> toStudent;


    public StudentService() {
        this.toStudentDto = new JMapper<>(StudentDTO.class, Student.class);
        this.toStudent = new JMapper<>(Student.class, StudentDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            StudentDTO studentDTO = toStudentDto.getDestination(student.get());
            responseDto.setObject(studentDTO);
        }
        return responseDto;
    }

    public ResponseDto create(StudentDTO studentDTO) {
        ResponseDto responseDto = new ResponseDto();
        Grade grade = gradeRepository.findById(studentDTO.getGradeId()).get();
        Student student = toStudent.getDestination(studentDTO);
        student.setId(genIdService.nextId());
        student.setGradeName(grade.getName());
        student.setPassword("123456");
        student.setIsDeleted(false);
        Student result = studentRepository.save(student);
        var temp = toStudentDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(StudentDTO studentDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Student> student = studentRepository.findById(studentDTO.getId());
        if (student.isPresent()) {
            Grade grade = gradeRepository.findById(studentDTO.getGradeId()).get();

            Student student1 = toStudent.getDestination(student.get(), studentDTO);
            student1.setGradeName(grade.getName());
            Student result = studentRepository.save(student1);
            StudentDTO studentDTO1 = toStudentDto.getDestination(result);
            responseDto.setObject(studentDTO1);
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
            studentDTOS.add(toStudentDto.getDestination(student));
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
            sql += "S-fullName=L\"" + search + "\", OR-S-gradeName=L\"" + search + "\"";
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
}
