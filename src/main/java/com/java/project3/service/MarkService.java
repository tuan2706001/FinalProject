package com.java.project3.service;


import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.MarkDTO;
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
    CourseClassRepository courseClassRepository;
    @Autowired
    CtdtSubjectClassRepository ctdtSubjectClassRepository;


    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public MarkService() {
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(id);
        if (mark.isPresent()) {
            MarkDTO markDTO = toMarkDto.getDestination(mark.get());
            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setSubjectName(subject.getName());
            responseDto.setObject(markDTO);
        }
        return responseDto;
    }

//    public ResponseDto findBySubjectId(Long subjectId) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CtdtSubject> subject = subjectRepository.findById(subjectId);
//        List<Mark> marks = markRepository.findBySubjectId(subject.get().getId());
//        List<MarkDTO> markDTOS = new ArrayList<>();
//        for (var item : marks) {
//            markDTOS.add(toMarkDto.getDestination(item));
//        }
//        responseDto.setObject(markDTOS);
//        return responseDto;
//    }

//    public ResponseDto findByGradeId(Long gradeId) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CourseClass> grade = courseClassRepository.findById(gradeId);
//        Mark marks = markRepository.findByGradeId(grade.get().getId());
////        List<MarkDTO> markDTOS = new ArrayList<>();
////        for (var item : marks) {
//            MarkDTO markDTO = toMarkDto.getDestination(marks);
////        }
//        responseDto.setObject(markDTO);
//        return responseDto;
//    }

//    public ResponseDto findByGradeAndSubject(Long gradeId, Long subjectId) {
//        ResponseDto responseDto = new ResponseDto();
//        List<Mark> marks = markRepository.findByGradeIdAndSubjectId(gradeId, subjectId);
//        List<MarkDTO> markDTOS = new ArrayList<>();
//        for (var item : marks) {
//            markDTOS.add(toMarkDto.getDestination(item));
//        }
//        responseDto.setObject(markDTOS);
//        return responseDto;
//    }


    public ResponseDto findByGradeId(Integer pageIndex, Integer pageSize, String seach, Long gradeId, Long ctdtSubjectClassId) {
        ResponseDto responseDto = new ResponseDto();
        Long id = gradeId;
        SearchReqDto searchReqDto = new SearchReqDto();
        List<String> sort = new ArrayList<>();
        sort.add("id");
        searchReqDto.setSorts(sort);
        searchReqDto.setQuery("");
        String sql = "";
        if (seach != null) {
            sql += id + "S-studentName=L\"" + seach + "\"";
        }
        if (ctdtSubjectClassId != null) {
            sql += id + "N-ctdtSubjectClassId=\"" + ctdtSubjectClassId + "\"";
        }

        searchReqDto.setQuery(sql);
        com.java.project3.dto.base.Page page = PageUltil.setDefault(pageIndex, pageSize);
        searchReqDto.setPageSize(page.getPageSize());
        searchReqDto.setPageIndex(page.getCurrentPage() - 1);
        responseDto = search(searchReqDto);
        return responseDto;
    }

//    public ResponseDto findBySubjectIdd (Long subjectId) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CtdtSubject> subject = subjectRepository.findById(subjectId);
//        List<Mark> marks = markRepository.findByGradeIdAndSubjectId(gradeId, subjectId);
//        List<MarkDTO> markDTOS = new ArrayList<>();
//        for (var item : marks) {
//            markDTOS.add(toMarkDto.getDestination(item));
//        }
//        responseDto.setObject(markDTOS);
//        return  responseDto;
//    }

    public ResponseDto create(MarkDTO markDTO) {
        ResponseDto responseDto = new ResponseDto();
        Mark mark = toMark.getDestination(markDTO);
        mark.setId(genIdService.nextId());
        if (mark.getTheory1() >= 5 && mark.getSkill1() >= 5) {
            mark.setStatus((int) 1);
        } else {
            mark.setStatus((int) 2);
        }
        Mark result = markRepository.save(mark);
        var temp = toMarkDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(MarkDTO markDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(markDTO.getId());
        if (mark.isPresent()) {
            Mark mark1 = toMark.getDestination(mark.get(), markDTO);
            if (mark1.getTheory1() < 5 && mark1.getSkill1() < 5) {
                if (mark1.getTheory2() >= 5 && mark1.getSkill2() >= 5) {
                    mark1.setStatus((int) 1);
                } else {
                    mark1.setStatus((int) 3);
                }
            }
            Mark result = markRepository.save(mark1);
            MarkDTO markDTO1 = toMarkDto.getDestination(result);
            responseDto.setObject(markDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
         ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Mark> marks = markRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkDTO> markDTOS = new ArrayList<>();
        for (var mark : marks) {
            MarkDTO markDTO = toMarkDto.getDestination(mark);
            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setSubjectName(subject.getName());
            markDTOS.add(markDTO);
        }
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), markDTOS));
        return responseDto;
    }

    public ResponseDto searchThongKe(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Mark> marks = markRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkDTO> markDTOS = new ArrayList<>();
        for (var mark : marks) {
            MarkDTO markDTO = toMarkDto.getDestination(mark);
            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setSubjectName(subject.getName());
            markDTOS.add(markDTO);
        }
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), markDTOS));
        return responseDto;
    }

    public ResponseDto searchMarkBy(Integer pageIndex, Integer pageSize, String search, Long gradeId, Integer status, Long subjectId) {
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
            sql += "S-subjectName=L\"" + search + "\", OR-S-studentName=L\"" + search + "\"";
        }
        if (status != null) {
            sql += ",N-status=\"" + status + "\"";
        }
        if (subjectId != null) {
            sql += ",N-subjectId=\"" + subjectId + "\"";
        }
        if (gradeId != null) {
            sql += ",N-gradeId=\"" + gradeId + "\"";
        }

        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = markService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(id);
        if (mark.isPresent()) {
            markRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

}
