package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.CtdtSubjectClass;
import com.java.project3.domain.MarkRetest;
import com.java.project3.domain.Student;
import com.java.project3.domain.Subject;
import com.java.project3.dto.MarkRetestDTO;
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
import static com.java.project3.utils.SearchUtil.prepareResponseForSearch;
import static org.springframework.data.domain.Sort.by;

@Service
public class MarkRetestService {
    @Autowired
    MarkRetestRepository markRetestRepository;
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


    JMapper<MarkRetestDTO, MarkRetest> toMarkRetestDto;
    JMapper<MarkRetest, MarkRetestDTO> toMarkRetest;


    public MarkRetestService() {
        this.toMarkRetestDto = new JMapper<>(MarkRetestDTO.class, MarkRetest.class);
        this.toMarkRetest = new JMapper<>(MarkRetest.class, MarkRetestDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<MarkRetest> markRetest = markRetestRepository.findById(id);
        if (markRetest.isPresent()) {
            MarkRetestDTO markRetestDTO = toMarkRetestDto.getDestination(markRetest.get());
            Student student = studentRepository.findById(markRetestDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markRetestDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markRetestDTO.getCtdtSubjectClassId());
            markRetestDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markRetestDTO.setStudentName(student.getFullName());
            markRetestDTO.setSubjectName(subject.getName());
            responseDto.setObject(markRetestDTO);
        }
        return responseDto;
    }


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
//        List<MarkRetest> markRetests = markRetestRepository.findByGradeIdAndSubjectId(gradeId, subjectId);
//        List<MarkRetestDTO> markRetestDTOS = new ArrayList<>();
//        for (var item : markRetests) {
//            markRetestDTOS.add(toMarkRetestDto.getDestination(item));
//        }
//        responseDto.setObject(markRetestDTOS);
//        return  responseDto;
//    }

    public ResponseDto create(MarkRetestDTO markRetestDTO) {
        ResponseDto responseDto = new ResponseDto();
        MarkRetest markRetest = toMarkRetest.getDestination(markRetestDTO);
        markRetest.setId(genIdService.nextId());
        if (markRetest.getRetestTheory() >= 5 && markRetest.getRetestSkill() >= 5) {
            markRetest.setStatus((int) 1);
        } else {
            markRetest.setStatus((int) 2);
        }
        MarkRetest result = markRetestRepository.save(markRetest);
        var temp = toMarkRetestDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

//    public ResponseDto update(MarkRetestDTO markRetestDTO) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<MarkRetest> markRetest = markRetestRepository.findById(markRetestDTO.getId());
//        if (markRetest.isPresent()) {
//            MarkRetest markRetest1 = toMarkRetest.getDestination(markRetest.get(), markRetestDTO);
//            if (markRetest1.getTheory1() < 5 && markRetest1.getSkill1() < 5) {
//                if (markRetest1.getTheory2() >= 5 && markRetest1.getSkill2() >= 5) {
//                    markRetest1.setStatus((int) 1);
//                } else {
//                    markRetest1.setStatus((int) 3);
//                }
//            }
//            MarkRetest result = markRetestRepository.save(markRetest1);
//            MarkRetestDTO markRetestDTO1 = toMarkRetestDto.getDestination(result);
//            responseDto.setObject(markRetestDTO1);
//        }
//        return responseDto;
//    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<MarkRetest> markRetests = markRetestRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkRetestDTO> markRetestDTOS = new ArrayList<>();
        for (var markRetest : markRetests) {
            MarkRetestDTO markRetestDTO = toMarkRetestDto.getDestination(markRetest);
            Student student = studentRepository.findById(markRetestDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markRetestDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markRetestDTO.getCtdtSubjectClassId());
            markRetestDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markRetestDTO.setStudentName(student.getFullName());
            markRetestDTO.setSubjectName(subject.getName());
            markRetestDTOS.add(markRetestDTO);
        }
        responseDto.setObject(prepareResponseForSearch(markRetests.getTotalPages(), markRetests.getNumber(), markRetests.getTotalElements(), markRetestDTOS));
        return responseDto;
    }

    public ResponseDto searchThongKe(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<MarkRetest> markRetests = markRetestRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkRetestDTO> markRetestDTOS = new ArrayList<>();
        for (var markRetest : markRetests) {
            MarkRetestDTO markRetestDTO = toMarkRetestDto.getDestination(markRetest);
            Student student = studentRepository.findById(markRetestDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markRetestDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markRetestDTO.getCtdtSubjectClassId());
            markRetestDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markRetestDTO.setStudentName(student.getFullName());
            markRetestDTO.setSubjectName(subject.getName());
            markRetestDTOS.add(markRetestDTO);
        }
        responseDto.setObject(prepareResponseForSearch(markRetests.getTotalPages(), markRetests.getNumber(), markRetests.getTotalElements(), markRetestDTOS));
        return responseDto;
    }

    public ResponseDto searchMarkRetestBy(Integer pageIndex, Integer pageSize, String search, Long gradeId, Integer status, Long subjectId) {
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
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<MarkRetest> markRetest = markRetestRepository.findById(id);
        if (markRetest.isPresent()) {
            markRetestRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }
}

