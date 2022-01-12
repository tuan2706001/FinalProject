package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Course;
import com.java.project3.dto.CourseDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseRepository;
import com.java.project3.utils.PageUltil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.java.project3.constant.Constants.DEFAULT_PROP;
import static com.java.project3.utils.SearchUtil.*;
import static org.springframework.data.domain.Sort.by;

@Service
public class CourseServcice {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseServcice courseServcice;


    JMapper<CourseDTO, Course> toCourseDto;
    JMapper<Course, CourseDTO> toCourse;


    public CourseServcice() {
        this.toCourseDto = new JMapper<>(CourseDTO.class, Course.class);
        this.toCourse = new JMapper<>(Course.class, CourseDTO.class);
    }

    public ResponseDto create(CourseDTO courseDTO) {
        ResponseDto responseDto = new ResponseDto();
        Course course = toCourse.getDestination(courseDTO);
        Course result = courseRepository.save(course);
        var temp = toCourseDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Course> courses = courseRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (var khoa : courses) {
            courseDTOS.add(toCourseDto.getDestination(khoa));
        }
        responseDto.setObject(prepareResponseForSearch(courses.getTotalPages(), courses.getNumber(), courses.getTotalElements(), courseDTOS));
        return responseDto;
    }

    public ResponseDto searchCourseBy(Integer pageIndex, Integer pageSize, String name) {
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
        responseDto = courseServcice.search(searchReqDto);
        return responseDto;
    }


}
