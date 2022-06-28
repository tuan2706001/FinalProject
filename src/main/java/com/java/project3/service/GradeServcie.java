//package com.java.project3.service;
//
//import com.googlecode.jmapper.JMapper;
//import com.java.project3.domain.*;
//import com.java.project3.dto.CourseClassDTO;
//import com.java.project3.dto.MarkDTO;
//import com.java.project3.dto.StudentDTO;
//import com.java.project3.dto.base.ResponseDto;
//import com.java.project3.dto.base.SearchReqDto;
//import com.java.project3.repository.*;
//import com.java.project3.service.base.GenIdService;
//import com.java.project3.utils.PageUltil;
//import lombok.var;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.java.project3.constant.Constants.DEFAULT_PROP;
//import static com.java.project3.utils.SearchUtil.*;
//import static org.springframework.data.domain.Sort.by;
//
//@Service
//public class GradeServcie {
//    @Autowired
//    GradeServcie gradeServcie;
//    @Autowired
//    GradeRepository gradeRepository;
//    @Autowired
//    GenIdService genIdService;
//    @Autowired
//    MajorRepository majorRepository;
//    @Autowired
//    CourseRepository courseRepository;
//    @Autowired
//    StudentRepository studentRepository;
//    @Autowired
//    SubjectRepository subjectRepository;
//    @Autowired
//    MarkRepository markRepository;
//
//    JMapper<CourseClassDTO, CourseClass> toGradeDto;
//    JMapper<CourseClass, CourseClassDTO> toGrade;
//    JMapper<StudentDTO, Student> toStudentDto;
//    JMapper<Student, StudentDTO> toStudent;
//    JMapper<MarkDTO, Mark> toMarkDto;
//    JMapper<Mark, MarkDTO> toMark;
//
//
//    public GradeServcie() {
//        this.toGradeDto = new JMapper<>(CourseClassDTO.class, CourseClass.class);
//        this.toGrade = new JMapper<>(CourseClass.class, CourseClassDTO.class);
//        this.toStudentDto = new JMapper<>(StudentDTO.class, Student.class);
//        this.toStudent = new JMapper<>(Student.class, StudentDTO.class);
//        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
//        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
//    }
//
//    public ResponseDto findById(Long id) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CourseClass> grade = gradeRepository.findById(id);
//        if (grade.isPresent()) {
//            CourseClassDTO courseClassDTO = toGradeDto.getDestination(grade.get());
//            responseDto.setObject(courseClassDTO);
//        }
//        return responseDto;
//    }
//
//    public ResponseDto create(CourseClassDTO courseClassDTO) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<Major> major = majorRepository.findById(courseClassDTO.getMajorId());
//        CourseClass courseClass = toGrade.getDestination(courseClassDTO);
//        courseClass.setId(genIdService.nextId());
//        courseClass.setMajorName(major.get().getName());
//        CourseClass result = gradeRepository.save(courseClass);
//        var temp = toGradeDto.getDestination(result);
//        responseDto.setObject(temp);
//        return responseDto;
//    }
//
//    public ResponseDto update(CourseClassDTO courseClassDTO) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CourseClass> grade = gradeRepository.findById(courseClassDTO.getId());
//        if (grade.isPresent()) {
//            Major major = majorRepository.findById(courseClassDTO.getMajorId()).get();
//            CourseClass courseClass1 = toGrade.getDestination(grade.get(), courseClassDTO);
//            courseClass1.setMajorName(major.getName());
//            CourseClass result = gradeRepository.save(courseClass1);
//            CourseClassDTO courseClassDTO1 = toGradeDto.getDestination(result);
//            responseDto.setObject(courseClassDTO1);
//
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
//        }
//        return responseDto;
//    }
//
//    public ResponseDto search(SearchReqDto reqDto) {
//        ResponseDto responseDto = new ResponseDto();
//        // Dùng hàm search (hero)
//        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
//                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
//        Page<CourseClass> grades = gradeRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
//        // entity -> dto
//        List<CourseClassDTO> courseClassDTOS = new ArrayList<>();
//        for (var grade : grades) {
//            CourseClassDTO courseClassDTO = toGradeDto.getDestination(grade);
//            Major major = majorRepository.findById(courseClassDTO.getMajorId()).orElse(null);
//            Course course = courseRepository.findById(major.getCourseId()).orElse(null);
//            courseClassDTO.setCourseName(course.getName());
//            courseClassDTO.setSumStudent(studentRepository.countStudentByGradeId(grade.getId()));
//            courseClassDTOS.add(courseClassDTO);
//        }
//        responseDto.setObject(prepareResponseForSearch(grades.getTotalPages(), grades.getNumber(), grades.getTotalElements(), courseClassDTOS));
//        return responseDto;
//    }
//
//    public ResponseDto searchGradeBy(Integer pageIndex, Integer pageSize, String search, Long courseId, Long majorId) {
//        ResponseDto responseDto = new ResponseDto();
//        SearchReqDto searchReqDto = new SearchReqDto();
//        com.java.project3.dto.base.Page
//                page = PageUltil.setDefault(pageIndex, pageSize);
//        searchReqDto.setPageIndex(page.getCurrentPage() - 1);
//        searchReqDto.setPageSize(page.getPageSize());
//        List<String> sort = new ArrayList<>();
//        sort.add("id");
//        searchReqDto.setSorts(sort);
//        String sql = "";
//        if (search != null) {
//            sql += "S-name=L\"" + search + "\", OR-S-majorName=L\"" + search + "\"";
//        }
//        if (majorId != null) {
//            sql += ",N-majorId=\"" + majorId + "\"";
//        }
//        searchReqDto.setQuery(sql);
//        searchReqDto.setPageSize(pageSize);
//        searchReqDto.setPageIndex(pageIndex);
//        responseDto = gradeServcie.search(searchReqDto);
//        return responseDto;
//    }
//
//    public ResponseDto delete(Long id) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CourseClass> grade = gradeRepository.findById(id);
//        if (grade.isPresent()) {
//            Student student = studentRepository.delete(id);
//            if (student == null) {
//                gradeRepository.deleteById(id);
//                responseDto.setObject(id);
//            }
//        }
//        return responseDto;
//    }
//
//    public long countAll() {
//        long grade = gradeRepository.count();
//        return grade;
//    }
//
//
//}
