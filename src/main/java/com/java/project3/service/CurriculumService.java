package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Course;
import com.java.project3.domain.Curriculum;
import com.java.project3.domain.Major;
import com.java.project3.dto.CourseDTO;
import com.java.project3.dto.CurriculumDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseRepository;
import com.java.project3.repository.CurriculumRepository;
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
public class CurriculumService {
    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    GenIdService genIdService;


    JMapper<CurriculumDTO, Curriculum> toCurriculumDto;
    JMapper<Curriculum, CurriculumDTO> toCurriculum;


    public CurriculumService() {
        this.toCurriculumDto = new JMapper<>(CurriculumDTO.class, Curriculum.class);
        this.toCurriculum = new JMapper<>(Curriculum.class, CurriculumDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Curriculum> curriculum = curriculumRepository.findById(id);
        if (curriculum.isPresent()) {
            CurriculumDTO curriculumDTO = toCurriculumDto.getDestination(curriculum.get());
            responseDto.setObject(curriculumDTO);
        }
        return responseDto;
    }

    public ResponseDto create(CurriculumDTO curriculumDTO) {
        ResponseDto responseDto = new ResponseDto();
        Curriculum curriculum = toCurriculum.getDestination(curriculumDTO);
        curriculum.setId(genIdService.nextId());
        Curriculum result = curriculumRepository.save(curriculum);
        var temp = toCurriculumDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(CurriculumDTO curriculumDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Curriculum> curriculum = curriculumRepository.findById(curriculumDTO.getId());
        if (curriculum.isPresent()) {
            Curriculum curriculum1 = toCurriculum.getDestination(curriculum.get(), curriculumDTO);
            Curriculum result = curriculumRepository.save(curriculum1);
            CurriculumDTO curriculumDTO1 = toCurriculumDto.getDestination(result);
            responseDto.setObject(curriculumDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Curriculum> curricula = curriculumRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CurriculumDTO> curriculumDTOS = new ArrayList<>();
        for (var curriculum : curricula) {
            curriculumDTOS.add(toCurriculumDto.getDestination(curriculum));
        }
        responseDto.setObject(prepareResponseForSearch(curricula.getTotalPages(), curricula.getNumber(), curricula.getTotalElements(), curriculumDTOS));
        return responseDto;
    }

    public ResponseDto searchCurriculumBy(Integer pageIndex, Integer pageSize, String name) {
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
        Optional<Curriculum> curriculum = curriculumRepository.findById(id);
        if (curriculum.isPresent()) {
            curriculumRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

    public long countAll() {
        long count = curriculumRepository.count();
        return count;
    }
}
