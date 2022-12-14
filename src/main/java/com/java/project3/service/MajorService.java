package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Curriculum;
import com.java.project3.domain.Major;
import com.java.project3.domain.Subject;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseRepository;
import com.java.project3.repository.CurriculumRepository;
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
public class MajorService {
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MajorService majorService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CurriculumRepository curriculumRepository;


    JMapper<MajorDTO, Major> toMajorDto;
    JMapper<Major, MajorDTO> toMajor;
    JMapper<SubjectDTO, Subject> toSubjectDto;
    JMapper<Subject, SubjectDTO> toSubject;


    public MajorService() {
        this.toMajorDto = new JMapper<>(MajorDTO.class, Major.class);
        this.toMajor = new JMapper<>(Major.class, MajorDTO.class);
        this.toSubjectDto = new JMapper<>(SubjectDTO.class, Subject.class);
        this.toSubject = new JMapper<>(Subject.class, SubjectDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(id);
        if (major.isPresent()) {
            MajorDTO majorDTO = toMajorDto.getDestination(major.get());
            Curriculum curriculum = curriculumRepository.findById(majorDTO.getCurriculumId()).orElse(null);
            majorDTO.setCurriculumName(curriculum.getName());
            responseDto.setObject(majorDTO);
        }
        return responseDto;
    }


//    public ResponseDto findByCourseId(Long id) {
//        ResponseDto responseDto = new ResponseDto();
//        List<Major> major = majorRepository.findByCourseId(id);
//        List<MajorDTO> majorDTOS = new ArrayList<>();
//        for (var item : major) {
//            majorDTOS.add(toMajorDto.getDestination(item));
//        }
//        responseDto.setObject(majorDTOS);
//        return responseDto;
//    }

//    public ResponseDto findBySubjectId(Long id) {
//        ResponseDto responseDto = new ResponseDto();
//        Optional<CtdtSubject> subject = subjectRepository.findById(id);
//        List<CourseClass> grades = courseClassRepository.search(subject.get().getMajorId());
//        List<CourseClassDTO> gradeDTOS = new ArrayList<>();
//        for (var item : grades) {
//            gradeDTOS.add(toMajorDto.getDestination(item));
//        }
//        responseDto.setObject(majorDTOS);
//        return responseDto;
//    }


    public ResponseDto create(MajorDTO majorDTO) {
        ResponseDto responseDto = new ResponseDto();
        Major major = toMajor.getDestination(majorDTO);
        major.setId(genIdService.nextId());
        Major result = majorRepository.save(major);
        var temp = toMajorDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(MajorDTO majorDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(majorDTO.getId());
        if (major.isPresent()) {
            Major major1 = toMajor.getDestination(major.get(), majorDTO);
            Major result = majorRepository.save(major1);
            MajorDTO majorDTO1 = toMajorDto.getDestination(result);
            responseDto.setObject(majorDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // D??ng h??m search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Major> majors = majorRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MajorDTO> majorDTOS = new ArrayList<>();
        for (var major : majors) {
            MajorDTO majorDTO = toMajorDto.getDestination(major);
            Curriculum curriculum = curriculumRepository.findById(majorDTO.getCurriculumId()).orElse(null);
            majorDTO.setCurriculumName(curriculum.getName());
            majorDTOS.add(majorDTO);
        }
        responseDto.setObject(prepareResponseForSearch(majors.getTotalPages(), majors.getNumber(), majors.getTotalElements(), majorDTOS));
        return responseDto;
    }

    public ResponseDto searchMajorBy(Integer pageIndex, Integer pageSize, String name, Long curriculumId) {
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
        if (curriculumId != null) {
            sql += ",N-curriculumId=\"" + curriculumId + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(id);
        if (major.isPresent()) {
            majorRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long count = majorRepository.count();
        return count;
    }
}
