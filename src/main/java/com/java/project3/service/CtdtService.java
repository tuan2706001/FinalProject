package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Ctdt;
import com.java.project3.domain.CtdtSubject;
import com.java.project3.domain.Curriculum;
import com.java.project3.domain.Major;
import com.java.project3.dto.CtdtDTO;
import com.java.project3.dto.MajorDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.CourseRepository;
import com.java.project3.repository.CtdtRepository;
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
public class CtdtService {
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    GenIdService genIdService;
    @Autowired
    MajorService majorService;
    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    CtdtRepository ctdtRepository;
    @Autowired
    CtdtService ctdtService;


    JMapper<CtdtDTO, Ctdt> toCtdtDto;
    JMapper<Ctdt, CtdtDTO> toCtdt;


    public CtdtService() {
        this.toCtdtDto = new JMapper<>(CtdtDTO.class, Ctdt.class);
        this.toCtdt = new JMapper<>(Ctdt.class, CtdtDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Ctdt> ctdt = ctdtRepository.findById(id);
        if (ctdt.isPresent()) {
            CtdtDTO ctdtDTO = toCtdtDto.getDestination(ctdt.get());
            Major major = majorRepository.findById(ctdtDTO.getMajorId()).orElse(null);
            ctdtDTO.setMajorName(major.getName());
            responseDto.setObject(ctdtDTO);
        }
        return responseDto;
    }


    public ResponseDto findByMajorId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Ctdt> ctdts = ctdtRepository.findByMajorId(id);
        List<CtdtDTO> ctdtDTOS = new ArrayList<>();
        for (var item : ctdts) {
            ctdtDTOS.add(toCtdtDto.getDestination(item));
        }
        responseDto.setObject(ctdtDTOS);
        return responseDto;
    }

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


    public ResponseDto create(CtdtDTO ctdtDTO) {
        ResponseDto responseDto = new ResponseDto();
//        Major major = majorRepository.findById(ctdtDTO.getMajorId()).orElse(null);
        Ctdt ctdt = toCtdt.getDestination(ctdtDTO);
        ctdt.setId(genIdService.nextId());
//        ctdt.setMajorName(major.getName());
        Ctdt result = ctdtRepository.save(ctdt);
        var temp = toCtdtDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(CtdtDTO ctdtDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Ctdt> ctdt = ctdtRepository.findById(ctdtDTO.getId());
        if (ctdt.isPresent()) {
//            Major major = majorRepository.findById(ctdtDTO.getMajorId()).orElse(null);
            Ctdt ctdt1 = toCtdt.getDestination(ctdt.get(), ctdtDTO);
//            ctdt1.setMajorName(major.getName());
            Ctdt result = ctdtRepository.save(ctdt1);
            CtdtDTO ctdtDTO1 = toCtdtDto.getDestination(result);
            responseDto.setObject(ctdtDTO1);

//            //update lại tên ngành trong lớp
//            List<CourseClass> courseClasses = courseClassRepository.search(majorDTO.getId());
//            List<CourseClassDTO> courseClassDTOS = new ArrayList<>();
//            for (var item : courseClasses) {
//                courseClassDTOS.add(toGradeDto.getDestination(item));
//                item.setMajorName(major1.getName());
//                courseClassRepository.save(item);
//            }
//
//            //update lại tên ngành trong môn
//            List<CtdtSubject> ctdtSubjects = subjectRepository.findByMajorId(majorDTO.getId());
//            List<SubjectDTO> subjectDTOS = new ArrayList<>();
//            for (var item : ctdtSubjects) {
//                subjectDTOS.add(toSubjectDto.getDestination(item));
//                item.setMajorName(major1.getName());
//                subjectRepository.save(item);
//            }
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Ctdt> ctdts = ctdtRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<CtdtDTO> ctdtDTOS = new ArrayList<>();
        for (var ctdt : ctdts) {
            CtdtDTO ctdtDTO = toCtdtDto.getDestination(ctdt);
            Major major = majorRepository.findById(ctdtDTO.getMajorId()).orElse(null);
            ctdtDTO.setMajorName(major.getName());
            ctdtDTOS.add(ctdtDTO);
        }
        responseDto.setObject(prepareResponseForSearch(ctdts.getTotalPages(), ctdts.getNumber(), ctdts.getTotalElements(), ctdtDTOS));
        return responseDto;
    }

    public ResponseDto searchCtdtBy(Integer pageIndex, Integer pageSize, String name, Long majorId) {
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
        if (majorId != null) {
            sql += ",N-majorId=\"" + majorId + "\"";
        }
        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Ctdt> ctdt = ctdtRepository.findById(id);
        if (ctdt.isPresent()) {

            ctdtRepository.deleteById(id);
            responseDto.setObject(id);

        }
        return responseDto;
    }
}
