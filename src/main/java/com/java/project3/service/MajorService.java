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
    GradeRepository gradeRepository;


    JMapper<MajorDTO, Major> toMajorDto;
    JMapper<Major, MajorDTO> toMajor;


    public MajorService() {
        this.toMajorDto = new JMapper<>(MajorDTO.class, Major.class);
        this.toMajor = new JMapper<>(Major.class, MajorDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(id);
        if (major.isPresent()) {
            MajorDTO majorDTO = toMajorDto.getDestination(major.get());
            responseDto.setObject(majorDTO);
        }
        return responseDto;
    }


    public ResponseDto create(MajorDTO majorDTO) {
        ResponseDto responseDto = new ResponseDto();
        Course course = courseRepository.findById(majorDTO.getCourseId()).orElse(null);
        Major major = toMajor.getDestination(majorDTO);
        major.setId(genIdService.nextId());
        major.setCourseName(course.getName());
        major.setIsDeleted(false);
        Major result = majorRepository.save(major);
        var temp = toMajorDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(MajorDTO majorDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(majorDTO.getId());
        if (major.isPresent()) {
            Course course = courseRepository.findById(majorDTO.getCourseId()).orElse(null);
            Major major1 = toMajor.getDestination(major.get(), majorDTO);
            major1.setCourseName(course.getName());
            Major result = majorRepository.save(major1);
            MajorDTO majorDTO1 = toMajorDto.getDestination(result);
            responseDto.setObject(majorDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Major> majors = majorRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MajorDTO> majorDTOS = new ArrayList<>();
        for (var major : majors) {
            majorDTOS.add(toMajorDto.getDestination(major));
        }
        responseDto.setObject(prepareResponseForSearch(majors.getTotalPages(), majors.getNumber(), majors.getTotalElements(), majorDTOS));
        return responseDto;
    }

    public ResponseDto searchMajorBy(Integer pageIndex, Integer pageSize, String name) {
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
        responseDto = majorService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Major> major = majorRepository.findById(id);
        if (major.isPresent()) {
            Grade grade = gradeRepository.findByMajorId(id);
            if (grade == null) {
                majorRepository.deleteById(id);
                responseDto.setObject(id);
            }
        }
        return responseDto;
    }
}
