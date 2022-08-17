package com.java.project3.service;


import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.TeacherDTO;
import com.java.project3.dto.TeacherSubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.SubjectRepository;
import com.java.project3.repository.TeacherRepository;
import com.java.project3.repository.TearcherSubjectReposiotry;
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
import java.util.stream.Collectors;

import static com.java.project3.constant.Constants.DEFAULT_PROP;
import static com.java.project3.utils.SearchUtil.*;
import static org.springframework.data.domain.Sort.by;

@Service
public class TeacherService {
    @Autowired
    GenIdService genIdService;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TearcherSubjectReposiotry tearcherSubjectReposiotry;
    @Autowired
    SubjectRepository subjectRepository;

    JMapper<TeacherDTO, Teacher> toTeacherDto;
    JMapper<Teacher, TeacherDTO> toTeacher;
    JMapper<TeacherSubjectDTO, TeacherSubject> toTeacherSubjectDto;
    JMapper<TeacherSubject, TeacherSubjectDTO> toTeacherSubject;


    public TeacherService() {
        this.toTeacherDto = new JMapper<>(TeacherDTO.class, Teacher.class);
        this.toTeacher = new JMapper<>(Teacher.class, TeacherDTO.class);
        this.toTeacherSubjectDto = new JMapper<>(TeacherSubjectDTO.class, TeacherSubject.class);
        this.toTeacherSubject = new JMapper<>(TeacherSubject.class, TeacherSubjectDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            TeacherDTO teacherDTO = toTeacherDto.getDestination(teacher.get());
            responseDto.setObject(teacherDTO);
        }
        return responseDto;
    }

    public ResponseDto findByCtdtSubjectId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<TeacherSubject> teacherSubjects = tearcherSubjectReposiotry.findByCtdtSubjectId(id);
        List<TeacherSubjectDTO> teacherSubjectDTOS = new ArrayList<>();
        for (var item : teacherSubjects) {
            TeacherSubjectDTO teacherSubjectDTO = toTeacherSubjectDto.getDestination(item);
            Teacher teacher = teacherRepository.findById(teacherSubjectDTO.getTeacherId()).orElse(null);
            teacherSubjectDTO.setTeacherName(teacher.getName());
            teacherSubjectDTOS.add(teacherSubjectDTO);
        }
        responseDto.setObject(teacherSubjectDTOS);
        return responseDto;
    }

    public ResponseDto findTeacherByCtdtSubjectId(Long ctdtSubjectId) {
        ResponseDto responseDto = new ResponseDto();
        List<Teacher> teachers = teacherRepository.findTeacherByCtdtSubjectId(ctdtSubjectId);
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        for (var item : teachers) {
            teacherDTOS.add(toTeacherDto.getDestination(item));
        }
        responseDto.setObject(teacherDTOS);
        return responseDto;
    }

    public ResponseDto create(TeacherDTO teacherDTO) {
        ResponseDto responseDto = new ResponseDto();
        Teacher teacher = toTeacher.getDestination(teacherDTO);
        teacher.setId(genIdService.nextId());
        Teacher result = teacherRepository.save(teacher);
        var temp = toTeacherDto.getDestination(result);
        responseDto.setObject(temp);
        List<Long> listSubejct = teacherDTO.getSubjectIds();
        for (Long subjects : listSubejct) {
            teacherRepository.addSubjectToTeacher(genIdService.nextId(), result.getId(), subjects);
        }
        return responseDto;
    }

    public ResponseDto update(TeacherDTO teacherDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Teacher> teacher = teacherRepository.findById(teacherDTO.getId());
        if (teacher.isPresent()) {
            Teacher teacher1 = toTeacher.getDestination(teacher.get(), teacherDTO);
            Teacher result = teacherRepository.save(teacher1);
            TeacherDTO teacherDTO1 = toTeacherDto.getDestination(result);
            responseDto.setObject(teacherDTO1);

        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Teacher> teachers = teacherRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        for (var teacher : teachers) {
            TeacherDTO teacherDTO = toTeacherDto.getDestination(teacher);
            teacherDTO.setSubjectIds(subjectRepository.findSubjectByTeacher(teacher.getId()));

            teacherDTO.setSumSubject(tearcherSubjectReposiotry.countTeacherSubjectByTeacherId(teacher.getId()));

            List<Long> listSubejct = teacherDTO.getSubjectIds();
//            for (Long subjectId : listSubejct) {
//                teacherDTO.setSubjectNames(subjectRepository.findNameBySubjectId(subjectId));
                List<String> subjectNames = listSubejct.stream().
                        map(subjectId -> subjectRepository.findNameBySubjectId(subjectId)).
                        collect(Collectors.toList());
                teacherDTO.setSubjectNames(subjectNames);
//            }
            teacherDTOS.add(teacherDTO);
        }
        responseDto.setObject(prepareResponseForSearch(teachers.getTotalPages(), teachers.getNumber(), teachers.getTotalElements(), teacherDTOS));
        return responseDto;
    }

    public ResponseDto searchTeacherBy(Integer pageIndex, Integer pageSize, String name) {
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
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            teacherRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long count = teacherRepository.count();
        return count;
    }
}
