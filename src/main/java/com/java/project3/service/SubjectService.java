package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.SubjectGeneral;
import com.java.project3.dto.SubjectGeneralDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
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
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GenIdService genIdService;


    JMapper<SubjectGeneralDTO, SubjectGeneral> toSubjectDto;
    JMapper<SubjectGeneral, SubjectGeneralDTO> toSubject;


    public SubjectService() {
        this.toSubjectDto = new JMapper<>(SubjectGeneralDTO.class, SubjectGeneral.class);
        this.toSubject = new JMapper<>(SubjectGeneral.class, SubjectGeneralDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<SubjectGeneral> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            SubjectGeneralDTO subjectGeneralDTO = toSubjectDto.getDestination(subject.get());
            responseDto.setObject(subjectGeneralDTO);
        }
        return responseDto;
    }

    public ResponseDto create(SubjectGeneralDTO subjectGeneralDTO) {
        ResponseDto responseDto = new ResponseDto();
        SubjectGeneral subjectGeneral = toSubject.getDestination(subjectGeneralDTO);
        subjectGeneral.setId(genIdService.nextId());
        subjectGeneral.setIsDeleted(false);
        SubjectGeneral result = subjectRepository.save(subjectGeneral);
        var temp = toSubjectDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(SubjectGeneralDTO subjectGeneralDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<SubjectGeneral> subject = subjectRepository.findById(subjectGeneralDTO.getId());
        if (subject.isPresent()) {
            SubjectGeneral subjectGeneral1 = toSubject.getDestination(subject.get(), subjectGeneralDTO);
            SubjectGeneral result = subjectRepository.save(subjectGeneral1);
            SubjectGeneralDTO subjectGeneralDTO1 = toSubjectDto.getDestination(result);
            responseDto.setObject(subjectGeneralDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<SubjectGeneral> subjectGenerals = subjectRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<SubjectGeneralDTO> subjectGeneralDTOS = new ArrayList<>();
        for (var subjectGeneral : subjectGenerals) {
            subjectGeneralDTOS.add(toSubjectDto.getDestination(subjectGeneral));
        }
        responseDto.setObject(prepareResponseForSearch(subjectGenerals.getTotalPages(), subjectGenerals.getNumber(), subjectGenerals.getTotalElements(), subjectGeneralDTOS));
        return responseDto;
    }

    public ResponseDto searchSubjectBy(Integer pageIndex, Integer pageSize, String search) {
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
            sql = "S-name=L\"" + search + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = subjectService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<SubjectGeneral> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            subjectRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }
}
