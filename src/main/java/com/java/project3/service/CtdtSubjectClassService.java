package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.CtdtSubjectClassDTO;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseClassRepository;
import com.java.project3.repository.CtdtSubjectClassRepository;
import com.java.project3.repository.CtdtSubjectRepository;
import com.java.project3.repository.TeacherRepository;
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
public class CtdtSubjectClassService {
    @Autowired
    CtdtSubjectClassRepository ctdtSubjectClassRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    CourseClassRepository courseClassRepository;
    @Autowired
    CtdtSubjectRepository ctdtSubjectRepository;
    @Autowired
    TeacherRepository teacherRepository;


    JMapper<CtdtSubjectClassDTO, CtdtSubjectClass> toCtdtSubjectClassDto;
    JMapper<CtdtSubjectClass, CtdtSubjectClassDTO> toCtdtSubjectClass;


    public CtdtSubjectClassService() {
        this.toCtdtSubjectClassDto = new JMapper<>(CtdtSubjectClassDTO.class, CtdtSubjectClass.class);
        this.toCtdtSubjectClass = new JMapper<>(CtdtSubjectClass.class, CtdtSubjectClassDTO.class);
    }

    public ResponseDto findById(Long id) {
            ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubjectClass> ctdtSubjectClass = ctdtSubjectClassRepository.findById(id);
        if (ctdtSubjectClass.isPresent()) {
            CtdtSubjectClassDTO ctdtSubjectClassDTO = toCtdtSubjectClassDto.getDestination(ctdtSubjectClass.get());
            CourseClass courseClass = courseClassRepository.findById(ctdtSubjectClassDTO.getCourseClassId()).orElse(null);
            CtdtSubject ctdtSubject = ctdtSubjectRepository.findById(ctdtSubjectClassDTO.getCtdtSubjectId()).orElse(null);
            Teacher teacher = teacherRepository.findById(ctdtSubjectClassDTO.getTeacherId()).orElse(null);
            ctdtSubjectClassDTO.setTeacherName(teacher.getName());
            ctdtSubjectClassDTO.setCourseClassName(courseClass.getName());
            ctdtSubjectClassDTO.setCtdtSubjectName(ctdtSubjectRepository.findNameByCtdtSubjectId(ctdtSubject.getId()));
            responseDto.setObject(ctdtSubjectClassDTO);
        }
        return responseDto;
    }

    public ResponseDto create(CtdtSubjectClassDTO ctdtSubjectClassDTO) {
        ResponseDto responseDto = new ResponseDto();
        CtdtSubjectClass ctdtSubjectClass = toCtdtSubjectClass.getDestination(ctdtSubjectClassDTO);
        ctdtSubjectClass.setId(genIdService.nextId());
        CtdtSubjectClass result = ctdtSubjectClassRepository.save(ctdtSubjectClass);
        var temp = toCtdtSubjectClassDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(CtdtSubjectClassDTO ctdtSubjectClassDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubjectClass> ctdtSubjectClass = ctdtSubjectClassRepository.findById(ctdtSubjectClassDTO.getId());
        if (ctdtSubjectClass.isPresent()) {
            CtdtSubjectClass ctdtSubjectClass1 = toCtdtSubjectClass.getDestination(ctdtSubjectClass.get(), ctdtSubjectClassDTO);
            CtdtSubjectClass result = ctdtSubjectClassRepository.save(ctdtSubjectClass1);
            CtdtSubjectClassDTO ctdtSubjectClassDTO1 = toCtdtSubjectClassDto.getDestination(result);
            responseDto.setObject(ctdtSubjectClassDTO1);

        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<CtdtSubjectClass> ctdtSubjectClasses = ctdtSubjectClassRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CtdtSubjectClassDTO> ctdtSubjectClassDTOS = new ArrayList<>();
        for (var ctdtSubjectClass : ctdtSubjectClasses) {
            CtdtSubjectClassDTO ctdtSubjectClassDTO = toCtdtSubjectClassDto.getDestination(ctdtSubjectClass);
            CourseClass courseClass = courseClassRepository.findById(ctdtSubjectClassDTO.getCourseClassId()).orElse(null);
            CtdtSubject ctdtSubject = ctdtSubjectRepository.findById(ctdtSubjectClassDTO.getCtdtSubjectId()).orElse(null);
            Teacher teacher = teacherRepository.findById(ctdtSubjectClassDTO.getTeacherId()).orElse(null);
            ctdtSubjectClassDTO.setTeacherName(teacher.getName());
            ctdtSubjectClassDTO.setCourseClassName(courseClass.getName());
            ctdtSubjectClassDTO.setCtdtSubjectName(ctdtSubjectRepository.findNameByCtdtSubjectId(ctdtSubject.getId()));
            ctdtSubjectClassDTOS.add(ctdtSubjectClassDTO);
        }
        responseDto.setObject(prepareResponseForSearch(ctdtSubjectClasses.getTotalPages(), ctdtSubjectClasses.getNumber(), ctdtSubjectClasses.getTotalElements(), ctdtSubjectClassDTOS));
        return responseDto;
    }

    public ResponseDto searchCtdtSubjectClassBy(Integer pageIndex, Integer pageSize, String name, Long courseClassId) {
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
        if (name != null) {
            sql = "S-name=L\"" + name + "\"";
        }
        if (courseClassId != null) {
            sql += ",N-courseClassId=\"" + courseClassId + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubjectClass> ctdtSubjectClass = ctdtSubjectClassRepository.findById(id);
        if (ctdtSubjectClass.isPresent()) {
            ctdtSubjectClassRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long count = ctdtSubjectClassRepository.count();
        return count;
    }
}
