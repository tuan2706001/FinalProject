package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Subject;
import com.java.project3.dto.SubjectDTO;
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


    JMapper<SubjectDTO, Subject> toSubjectGeneralDto;
    JMapper<Subject, SubjectDTO> toSubjectGeneral;


    public SubjectService() {
        this.toSubjectGeneralDto = new JMapper<>(SubjectDTO.class, Subject.class);
        this.toSubjectGeneral = new JMapper<>(Subject.class, SubjectDTO.class);
    }

    public ResponseDto create(SubjectDTO subjectDTO) {
        ResponseDto responseDto = new ResponseDto();
        Subject subject = toSubjectGeneral.getDestination(subjectDTO);
        subject.setId(genIdService.nextId());
        subject.setIsDeleted(false);
        Subject result = subjectRepository.save(subject);
        var temp = toSubjectGeneralDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Subject> subjectGenerals = subjectRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (var subjectGeneral : subjectGenerals) {
            subjectDTOS.add(toSubjectGeneralDto.getDestination(subjectGeneral));
        }
        responseDto.setObject(prepareResponseForSearch(subjectGenerals.getTotalPages(), subjectGenerals.getNumber(), subjectGenerals.getTotalElements(), subjectDTOS));
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
}
