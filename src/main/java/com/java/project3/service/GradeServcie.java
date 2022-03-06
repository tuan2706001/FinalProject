package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.StudentDTO;
import com.java.project3.dto.SubjectDTO;
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
public class GradeServcie {
    @Autowired
    GradeServcie gradeServcie;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    MarkRepository markRepository;

    JMapper<GradeDTO, Grade> toGradeDto;
    JMapper<Grade, GradeDTO> toGrade;
    JMapper<StudentDTO, Student> toStudentDto;
    JMapper<Student, StudentDTO> toStudent;
    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public GradeServcie() {
        this.toGradeDto = new JMapper<>(GradeDTO.class, Grade.class);
        this.toGrade = new JMapper<>(Grade.class, GradeDTO.class);
        this.toStudentDto = new JMapper<>(StudentDTO.class, Student.class);
        this.toStudent = new JMapper<>(Student.class, StudentDTO.class);
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Grade> grade = gradeRepository.findById(id);
        if (grade.isPresent()) {
            GradeDTO gradeDTO = toGradeDto.getDestination(grade.get());
            responseDto.setObject(gradeDTO);
        }
        return responseDto;
    }

//    public ResponseDto findByMarkId(Long id) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<Mark> mark = markRepository.findById(id);
//        Grade grade = gradeRepository.fi(mark.get().getSubjectId());
//        GradeDTO gradeDTO = toGradeDto.getDestination(grade)
//
//        responseDto.setObject(gradeDTOS);
//        return responseDto;
//    }


    public ResponseDto create(GradeDTO gradeDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(gradeDTO.getMajorId());
        Grade grade = toGrade.getDestination(gradeDTO);
        grade.setId(genIdService.nextId());
        grade.setMajorName(major.get().getName());
        grade.setIsDeleted(false);
        Grade result = gradeRepository.save(grade);
        var temp = toGradeDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(GradeDTO gradeDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Grade> grade = gradeRepository.findById(gradeDTO.getId());
        if (grade.isPresent()) {
            Major major = majorRepository.findById(gradeDTO.getMajorId()).get();
            Grade grade1 = toGrade.getDestination(grade.get(), gradeDTO);
            grade1.setMajorName(major.getName());
            Grade result = gradeRepository.save(grade1);
            GradeDTO gradeDTO1 = toGradeDto.getDestination(result);
            responseDto.setObject(gradeDTO1);

            //update lại tên lớp trong student
            List<Student> students = studentRepository.findByGradeId(gradeDTO.getId());
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (var item : students) {
                studentDTOS.add(toStudentDto.getDestination(item));
                item.setGradeName(grade1.getName());
                studentRepository.save(item);
            }

            //update lại tên lớp trong điểm
            List<Mark> marks = markRepository.searchByGradeId(gradeDTO.getId());
            List<MarkDTO> markDTOS = new ArrayList<>();
            for (var item : marks) {
                markDTOS.add(toMarkDto.getDestination(item));
                item.setGradeName(grade1.getName());
                markRepository.save(item);
            }
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Grade> grades = gradeRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<GradeDTO> gradeDTOS = new ArrayList<>();
        for (var grade : grades) {
            gradeDTOS.add(toGradeDto.getDestination(grade));
        }
        responseDto.setObject(prepareResponseForSearch(grades.getTotalPages(), grades.getNumber(), grades.getTotalElements(), gradeDTOS));
        return responseDto;
    }

    public ResponseDto searchGradeBy(Integer pageIndex, Integer pageSize, String search, Long courseId, Long majorId) {
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
            sql += "S-name=L\"" + search + "\", OR-S-majorName=L\"" + search + "\"";
        }
        if (majorId != null) {
            sql += ",N-majorId=\"" + majorId + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = gradeServcie.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Grade> grade = gradeRepository.findById(id);
        if (grade.isPresent()) {
            Student student = studentRepository.delete(id);
            if (student == null) {
                gradeRepository.deleteById(id);
                responseDto.setObject(id);
            }
        }
        return responseDto;
    }

    public long countAll() {
        long grade = gradeRepository.count();
        return grade;
    }


}
