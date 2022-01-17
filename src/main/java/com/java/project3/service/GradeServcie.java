package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Course;
import com.java.project3.domain.Grade;
import com.java.project3.domain.Major;
import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseRepository;
import com.java.project3.repository.GradeRepository;
import com.java.project3.repository.MajorRepository;
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

    JMapper<GradeDTO, Grade> toGradeDto;
    JMapper<Grade, GradeDTO> toGrade;


    public GradeServcie() {
        this.toGradeDto = new JMapper<>(GradeDTO.class, Grade.class);
        this.toGrade = new JMapper<>(Grade.class, GradeDTO.class);
    }

    public ResponseDto create(GradeDTO gradeDTO) {
        ResponseDto responseDto = new ResponseDto();
        Major major = majorRepository.findById(gradeDTO.getMajorId()).get();
        Course course = courseRepository.findById(gradeDTO.getCourseId()).get();
        Grade grade = toGrade.getDestination(gradeDTO);
        grade.setId(genIdService.nextId());
        grade.setMajorName(major.getName());
        grade.setCourseName(course.getName());
        grade.setIsDeleted(false);
        Grade result = gradeRepository.save(grade);
        var temp = toGradeDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(GradeDTO gradeDTO) {
        ResponseDto responseDto = new ResponseDto();
        Major major = majorRepository.findById(gradeDTO.getMajorId()).get();
        Course course = courseRepository.findById(gradeDTO.getCourseId()).get();
        Optional<Grade> grade = gradeRepository.findById(gradeDTO.getId());
        if (grade.isPresent()) {
            Grade grade1 = toGrade.getDestination(grade.get(), gradeDTO);
            grade1.setMajorName(major.getName());
            grade1.setCourseName(course.getName());
            Grade result = gradeRepository.save(grade1);
            GradeDTO gradeDTO1 = toGradeDto.getDestination(result);
            responseDto.setObject(gradeDTO1);
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

    public ResponseDto searchGradeBy(Integer pageIndex, Integer pageSize, String search) {
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
            sql = "S-name=L\"" + search + "\", OR-S-majorName=L\"" + search + "\", OR-S-courseName=L\"" + search + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = gradeServcie.search(searchReqDto);
        return responseDto;
    }
}
