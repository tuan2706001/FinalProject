package com.java.project3.service;


import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.GradeDTO;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.GradeRepository;
import com.java.project3.repository.MarkRepository;
import com.java.project3.repository.StudentRepository;
import com.java.project3.repository.SubjectRepository;
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
public class MarkService {
    @Autowired
    MarkRepository markRepository;
    @Autowired
    MarkService markService;
    @Autowired
    GenIdService genIdService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    GradeRepository gradeRepository;


    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public MarkService() {
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto create(MarkDTO markDTO) {
        ResponseDto responseDto = new ResponseDto();
        Student student = studentRepository.findById(markDTO.getStudentId()).get();
        Subject subject = subjectRepository.findById(markDTO.getSubjectId()).get();
        Grade grade = gradeRepository.findById(markDTO.getGradeId()).get();
        Mark mark = toMark.getDestination(markDTO);
        mark.setId(genIdService.nextId());
        mark.setStudentName(student.getFullName());
        mark.setSubjectName(subject.getName());
        mark.setGradeName(grade.getName());
        mark.setIsDeleted(false);
        Mark result = markRepository.save(mark);
        var temp = toMarkDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

//    public ResponseDto update(MarkDTO markDTO) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<Mark> mark = markRepository.findById(markDTO.getId());
//        if (mark.isPresent()) {
//            Mark mark1 = markRepository.findById(markDTO.getMajorId()).get();
//            Course course = courseRepository.findById(gradeDTO.getCourseId()).get();
//
//            Grade grade1 = toGrade.getDestination(grade.get(), gradeDTO);
//            grade1.setMajorName(major.getName());
//            grade1.setCourseName(course.getName());
//            Grade result = gradeRepository.save(grade1);
//            GradeDTO gradeDTO1 = toGradeDto.getDestination(result);
//            responseDto.setObject(gradeDTO1);
//        }
//        return responseDto;
//    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Mark> marks = markRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkDTO> markDTOS = new ArrayList<>();
        for (var mark : marks) {
            markDTOS.add(toMarkDto.getDestination(mark));
        }
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), markDTOS));
        return responseDto;
    }

    public ResponseDto searchMarkBy(Integer pageIndex, Integer pageSize, String search) {
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
            sql = "S-subjectName=L\"" + search + "\", OR-S-studentName=L\"" + search + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = markService.search(searchReqDto);
        return responseDto;
    }
}
