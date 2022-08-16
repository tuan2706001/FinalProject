package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.domain.CtdtSubject;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.CtdtSubjectDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CtdtRepository;
import com.java.project3.repository.CtdtSubjectRepository;
import com.java.project3.repository.CtdtSubjectRepository;
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
public class CtdtSubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    CtdtSubjectService ctdtSubjectService;
    @Autowired
    GenIdService genIdService;
    @Autowired
    CtdtSubjectRepository ctdtSubjectRepository;
    @Autowired
    CtdtRepository  ctdtRepository;

    JMapper<CtdtSubjectDTO, CtdtSubject> toCtdtSubjectDto;
    JMapper<CtdtSubject, CtdtSubjectDTO> toCtdtSubject;
    JMapper<SubjectDTO, Subject> toSubjectDto;
    JMapper<Subject, SubjectDTO> toSubject;

    public CtdtSubjectService() {
        this.toCtdtSubjectDto = new JMapper<>(CtdtSubjectDTO.class, CtdtSubject.class);
        this.toCtdtSubject = new JMapper<>(CtdtSubject.class, CtdtSubjectDTO.class);
        this.toSubjectDto = new JMapper<>(SubjectDTO.class, Subject.class);
        this.toSubject = new JMapper<>(Subject.class, SubjectDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubject> ctdtSubject = ctdtSubjectRepository.findById(id);
        if (ctdtSubject.isPresent()) {
            CtdtSubjectDTO ctdtSubjectDTO = toCtdtSubjectDto.getDestination(ctdtSubject.get());
            Ctdt ctdt = ctdtRepository.findById(ctdtSubjectDTO.getCtdtId()).orElse(null);
            Subject subject = subjectRepository.findById(ctdtSubjectDTO.getSubjectId()).orElse(null);
            ctdtSubjectDTO.setCtdtName(ctdt.getName());
            ctdtSubjectDTO.setSubjectName(subject.getName());
            responseDto.setObject(ctdtSubjectDTO);
        }
        return responseDto;
    }

    public ResponseDto findByCtdtId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<CtdtSubject> ctdtSubjects = ctdtSubjectRepository.findByCtdtId(id);
        List<CtdtSubjectDTO> ctdtSubjectDTOS = new ArrayList<>();
        for (var item : ctdtSubjects) {
            CtdtSubjectDTO ctdtSubjectDTO = toCtdtSubjectDto.getDestination(item);
            Ctdt ctdt = ctdtRepository.findById(ctdtSubjectDTO.getCtdtId()).orElse(null);
            Subject subject = subjectRepository.findById(ctdtSubjectDTO.getSubjectId()).orElse(null);
            ctdtSubjectDTO.setCtdtName(ctdt.getName());
            ctdtSubjectDTO.setSubjectName(subject.getName());
            ctdtSubjectDTOS.add(ctdtSubjectDTO);
        }
        responseDto.setObject(ctdtSubjectDTOS);
        return responseDto;
    }


    public ResponseDto create(CtdtSubjectDTO ctdtSubjectDTO) {
        ResponseDto responseDto = new ResponseDto();
//        Ctdt ctdt = ctdtRepository.findById(ctdtSubjectDTO.getCtdtId()).orElse(null);
//        Subject subject = subjectRepository.findById(ctdtSubjectDTO.getSubjectId()).orElse(null);
        CtdtSubject ctdtSubject = toCtdtSubject.getDestination(ctdtSubjectDTO);
        ctdtSubject.setId(genIdService.nextId());
//        ctdtSubject.setSubjectName(subject.getName());
        CtdtSubject result = ctdtSubjectRepository.save(ctdtSubject);
        var temp = toCtdtSubjectDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }


    public ResponseDto update(CtdtSubjectDTO ctdtSubjectDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubject> ctdtSubject = ctdtSubjectRepository.findById(ctdtSubjectDTO.getId());
        if (ctdtSubject.isPresent()) {
            CtdtSubject ctdtSubject1 = toCtdtSubject.getDestination(ctdtSubject.get(), ctdtSubjectDTO);
            CtdtSubject result = ctdtSubjectRepository.save(ctdtSubject1);
            CtdtSubjectDTO ctdtSubjectDTO1 = toCtdtSubjectDto.getDestination(result);
            responseDto.setObject(ctdtSubjectDTO1);

        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<CtdtSubject> ctdtSubjects = ctdtSubjectRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CtdtSubjectDTO> ctdtSubjectDTOS = new ArrayList<>();
        for (var ctdtSubject : ctdtSubjects) {
            CtdtSubjectDTO ctdtSubjectDTO = toCtdtSubjectDto.getDestination(ctdtSubject);
            Ctdt ctdt = ctdtRepository.findById(ctdtSubjectDTO.getCtdtId()).orElse(null);
            Subject subject = subjectRepository.findById(ctdtSubjectDTO.getSubjectId()).orElse(null);
            ctdtSubjectDTO.setCtdtName(ctdt.getName());
            ctdtSubjectDTO.setSubjectName(subject.getName());
            ctdtSubjectDTOS.add(ctdtSubjectDTO);
        }
        responseDto.setObject(prepareResponseForSearch(ctdtSubjects.getTotalPages(), ctdtSubjects.getNumber(), ctdtSubjects.getTotalElements(), ctdtSubjectDTOS));
        return responseDto;
    }




    public ResponseDto searchCtdtSubjectBy(Integer pageIndex, Integer pageSize, String search, String type) {
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
        responseDto = ctdtSubjectService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CtdtSubject> ctdtSubject = ctdtSubjectRepository.findById(id);
        if (ctdtSubject.isPresent()) {
            ctdtSubjectRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

}
