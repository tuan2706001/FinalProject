package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.CourseClassDTO;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.*;
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
public class CourseClassServcie {
    @Autowired
    CourseClassServcie courseClassServcie;
    @Autowired
    CourseClassRepository courseClassRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CtdtRepository ctdtRepository;
    @Autowired
    StudentRepository studentRepository;
//    @Autowired
//    SubjectRepository subjectRepository;
//    @Autowired
//    MarkRepository markRepository;

    JMapper<CourseClassDTO, CourseClass> toCourseClassDto;
    JMapper<CourseClass, CourseClassDTO> toCourseClass;
    JMapper<StudentDTO, Student> toStudentDto;
    JMapper<Student, StudentDTO> toStudent;
    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public CourseClassServcie() {
        this.toCourseClassDto = new JMapper<>(CourseClassDTO.class, CourseClass.class);
        this.toCourseClass = new JMapper<>(CourseClass.class, CourseClassDTO.class);
        this.toStudentDto = new JMapper<>(StudentDTO.class, Student.class);
        this.toStudent = new JMapper<>(Student.class, StudentDTO.class);
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CourseClass> grade = courseClassRepository.findById(id);
        if (grade.isPresent()) {
            CourseClassDTO courseClassDTO = toCourseClassDto.getDestination(grade.get());
            responseDto.setObject(courseClassDTO);
        }
        return responseDto;
    }

    public ResponseDto create(CourseClassDTO courseClassDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Ctdt> ctdt = ctdtRepository.findById(courseClassDTO.getCtdtId());
        Optional<Course> course = courseRepository.findById(courseClassDTO.getCourseId());
        CourseClass courseClass = toCourseClass.getDestination(courseClassDTO);
        courseClass.setId(genIdService.nextId());
        courseClass.setCtdtName(ctdt.get().getName());
        courseClass.setCourseName(course.get().getName());
        CourseClass result = courseClassRepository.save(courseClass);
        var temp = toCourseClassDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(CourseClassDTO courseClassDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CourseClass> courseClass = courseClassRepository.findById(courseClassDTO.getId());
        if (courseClass.isPresent()) {
            Ctdt ctdt = ctdtRepository.findById(courseClassDTO.getCtdtId()).get();
            Course course = courseRepository.findById(courseClassDTO.getCourseId()).get();
            CourseClass courseClass1 = toCourseClass.getDestination(courseClass.get(), courseClassDTO);
            courseClass1.setCtdtName(ctdt.getName());
            courseClass1.setCourseName(course.getName());
            CourseClass result = courseClassRepository.save(courseClass1);
            CourseClassDTO courseClassDTO1 = toCourseClassDto.getDestination(result);
            responseDto.setObject(courseClassDTO1);

//            //update lại tên lớp trong student
//            List<Student> students = studentRepository.findByGradeId(courseClassDTO.getId());
//            List<StudentDTO> studentDTOS = new ArrayList<>();
//            for (var item : students) {
//                studentDTOS.add(toStudentDto.getDestination(item));
//                item.setGradeName(courseClass1.getName());
//                studentRepository.save(item);
//            }
//
//            //update lại tên lớp trong điểm
//            List<Mark> marks = markRepository.searchByGradeId(courseClassDTO.getId());
//            List<MarkDTO> markDTOS = new ArrayList<>();
//            for (var item : marks) {
//                markDTOS.add(toMarkDto.getDestination(item));
//                item.setGradeName(courseClass1.getName());
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
        Page<CourseClass> courseClasses = courseClassRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CourseClassDTO> courseClassDTOS = new ArrayList<>();
        for (var courseClass : courseClasses) {
            CourseClassDTO courseClassDTO = toCourseClassDto.getDestination(courseClass);



            Ctdt ctdt = ctdtRepository.findById(courseClassDTO.getCtdtId()).orElse(null);
            Course course = courseRepository.findById(courseClassDTO.getCourseId()).orElse(null);
            courseClassDTO.setCourseName(course.getName());
            courseClassDTO.setCtdtName(ctdt.getName());
            courseClassDTO.setSumStudent(studentRepository.countStudentByCourseClassId(courseClass.getId()));
            courseClassDTOS.add(courseClassDTO);
        }
        responseDto.setObject(prepareResponseForSearch(courseClasses.getTotalPages(), courseClasses.getNumber(), courseClasses.getTotalElements(), courseClassDTOS));
        return responseDto;
    }

    public ResponseDto searchCourseClassBy(Integer pageIndex, Integer pageSize, String search, Long courseId) {
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
            sql += "S-name=L\"";
        }
        if (courseId != null) {
            sql += ",N-courseId=\"" + courseId + "\"";
        }
//        if (ctdtId != null) {
//            sql += ",N-ctdtId=\"" + ctdtId + "\"";
//        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = courseClassServcie.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CourseClass> grade = courseClassRepository.findById(id);
        if (grade.isPresent()) {
//            Student student = studentRepository.delete(id);
//            if (student == null) {
                courseClassRepository.deleteById(id);
                responseDto.setObject(id);
//            }
        }
        return responseDto;
    }

    public long countAll() {
        long grade = courseClassRepository.count();
        return grade;
    }


}
