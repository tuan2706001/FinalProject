package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.*;
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
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseClassRepository courseClassRepository;


    JMapper<SubjectDTO, Subject> toSubjectDto;
    JMapper<Subject, SubjectDTO> toSubject;
    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public SubjectService() {
        this.toSubjectDto = new JMapper<>(SubjectDTO.class, Subject.class);
        this.toSubject = new JMapper<>(Subject.class, SubjectDTO.class);
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            SubjectDTO subjectDTO = toSubjectDto.getDestination(subject.get());
            responseDto.setObject(subjectDTO);
        }
        return responseDto;
    }

    public ResponseDto findByTeacherId(Long teacherId) {
        ResponseDto responseDto = new ResponseDto();
        List<Subject> subjects = subjectRepository.findByTeacherId(teacherId);
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (var item : subjects) {
            subjectDTOS.add(toSubjectDto.getDestination(item));
        }
        responseDto.setObject(subjectDTOS);
        return responseDto;
    }


    public ResponseDto findByCscId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Subject> subjects = subjectRepository.findByCtdtSubjectClassId(id);
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (var item : subjects) {
            subjectDTOS.add(toSubjectDto.getDestination(item));
        }
        responseDto.setObject(subjectDTOS);
        return responseDto;
    }

    public ResponseDto create(SubjectDTO subjectDTO) {
        ResponseDto responseDto = new ResponseDto();
        Subject subject = toSubject.getDestination(subjectDTO);
        subject.setId(genIdService.nextId());
        Subject result = subjectRepository.save(subject);
        var temp = toSubjectDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }


//    public ResponseDto createDetail(SubjectDTO subjectDTO) {
//        ResponseDto responseDto = new ResponseDto();
//        CtdtSubject ctdtSubject = toSubject.getDestination(subjectDTO);
//        ctdtSubject.setId(genIdService.nextId());
//        CtdtSubject result = subjectRepository.save(ctdtSubject);
//        var temp = toSubjectDto.getDestination(result);
//        responseDto.setObject(temp);
//        return responseDto;
//    }


    public ResponseDto update(SubjectDTO subjectDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Subject> subject = subjectRepository.findById(subjectDTO.getId());
        if (subject.isPresent()) {
            Subject subject1 = toSubject.getDestination(subject.get(), subjectDTO);
            Subject result = subjectRepository.save(subject1);
            SubjectDTO subjectDTO1 = toSubjectDto.getDestination(result);
            responseDto.setObject(subjectDTO1);

//            //update l???i m??n trong ??i???m
//            List<Mark> marks = markRepository.findBySubjectId(subjectDTO.getId());
//            List<MarkDTO> markDTOS = new ArrayList<>();
//            for (var item : marks) {
//                markDTOS.add(toMarkDto.getDestination(item));
//                item.setSubjectName(ctdtSubject1.getName());
//                markRepository.save(item);
//            }
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // D??ng h??m search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Subject> subjects = subjectRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (var subject : subjects) {
//            if (subject.getMajorId() != null) {
//                SubjectDTO subjectDTO = toSubjectDto.getDestination(subject);
//                Major major = majorRepository.findById(subjectDTO.getMajorId()).get();
//                Course course = courseRepository.findById(major.getCourseId()).get();
//                subjectDTO.setCourseName(course.getName());
//                subjectDTOS.add(subjectDTO);
//            } else {
                subjectDTOS.add(toSubjectDto.getDestination(subject));
//            }
        }
        responseDto.setObject(prepareResponseForSearch(subjects.getTotalPages(), subjects.getNumber(), subjects.getTotalElements(), subjectDTOS));
        return responseDto;
    }

//    public ResponseDto searchSubjectByType1(String type, String search, Integer pageSize, Integer pageIndex) {
//        ResponseDto responseDto = new ResponseDto();
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
//        type = type == null ? "" : type;
//        search = search == null ? "" : search.trim();
//        Page<CtdtSubject> subjects = subjectRepository.search1(type, search, pageable);
//        List<SubjectDTO> listDto = new ArrayList<>();
//        for (var item : subjects) {
//            listDto.add(toSubjectDto.getDestination(item));
//        }
//        responseDto.setObject(prepareResponseForSearch(subjects.getTotalPages(), subjects.getNumber(), subjects.getTotalElements(), listDto));
//        return responseDto;
//    }
//
//
//    public ResponseDto searchSubjectByType2(String type, String search, Integer pageSize, Integer pageIndex) {
//        ResponseDto responseDto = new ResponseDto();
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
//        type = type == null ? "" : type.trim();
//        search = search == null ? "" : search.trim();
//        Page<CtdtSubject> subjects = subjectRepository.search2(type, search, pageable);
//        List<SubjectDTO> listDto = new ArrayList<>();
//        for (var item : subjects) {
//            listDto.add(toSubjectDto.getDestination(item));
//        }
//        responseDto.setObject(prepareResponseForSearch(subjects.getTotalPages(), subjects.getNumber(), subjects.getTotalElements(), listDto));
//        return responseDto;
//    }

    public ResponseDto searchSubjectBy(Integer pageIndex, Integer pageSize, String search, String type) {
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
            sql += "S-name=L\"" + search + "\"";
        }
        if (type != null) {
            sql += "S-type=L\"" + type + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = subjectService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            subjectRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long count = subjectRepository.count();
        return count;
    }
}
