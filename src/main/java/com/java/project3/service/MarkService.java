package com.java.project3.service;


import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.ThongSoThongKeDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.*;
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
public class MarkService {
    @Autowired
    MarkRepository markRepository;
    @Autowired
    MarkService markService;
    @Autowired
    GenIdService genIdService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    CourseClassRepository courseClassRepository;
    @Autowired
    CtdtSubjectClassRepository ctdtSubjectClassRepository;
    @Autowired
    MarkRetestRepository markRetestRepository;


    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public MarkService() {
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findById(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(id);
        if (mark.isPresent()) {
            MarkDTO markDTO = toMarkDto.getDestination(mark.get());
            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setSubjectName(subject.getName());
            responseDto.setObject(markDTO);
        }
        return responseDto;
    }

//    public ResponseDto thongSoThongkeByCscId(Long cscId) {
//        ResponseDto responseDto = new ResponseDto();
//        ThongSoThongKeDTO thongSoThongKeDTO = new ThongSoThongKeDTO();
//        thongSoThongKeDTO.setSumQuaMon();
//        thongSoThongKeDTO.setSumThiLai();
//        thongSoThongKeDTO.setSumHocLai();
//        responseDto.setObject(thongSoThongKeDTO);
//        return responseDto;
//    }


    public ResponseDto findByMark(Integer pageIndex, Integer pageSize, String seach, Long gradeId, Long ctdtSubjectClassId) {
        ResponseDto responseDto = new ResponseDto();
        Long id = gradeId;
        SearchReqDto searchReqDto = new SearchReqDto();
        List<String> sort = new ArrayList<>();
        sort.add("id");
        searchReqDto.setSorts(sort);
        searchReqDto.setQuery("");
        String sql = "";
        if (seach != null) {
            sql += id + "S-studentName=L\"" + seach + "\"";
        }
        if (ctdtSubjectClassId != null) {
            sql += id + "N-ctdtSubjectClassId=\"" + ctdtSubjectClassId + "\"";
        }

        searchReqDto.setQuery(sql);
        com.java.project3.dto.base.Page page = PageUltil.setDefault(pageIndex, pageSize);
        searchReqDto.setPageSize(page.getPageSize());
        searchReqDto.setPageIndex(page.getCurrentPage() - 1);
        responseDto = search(searchReqDto);
        return responseDto;
    }

    public ResponseDto create(MarkDTO markDTO) {
        ResponseDto responseDto = new ResponseDto();
        Mark mark = toMark.getDestination(markDTO);
        mark.setId(genIdService.nextId());
        if (mark.getTheory1() >= 5 && mark.getSkill1() >= 5) {
            mark.setStatus((int) 1);
        } else {
            mark.setStatus((int) 2);
        }
        Mark result = markRepository.save(mark);
        var temp = toMarkDto.getDestination(result);
        responseDto.setObject(temp);
        return responseDto;
    }

    public ResponseDto update(MarkDTO markDTO) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(markDTO.getId());
        if (mark.isPresent()) {
            Mark mark1 = toMark.getDestination(mark.get(), markDTO);
            if (mark1.getTheory1() < 5 && mark1.getSkill1() < 5) {
                if (mark1.getTheory2() >= 5 && mark1.getSkill2() >= 5) {
                    mark1.setStatus((int) 1);
                } else {
                    mark1.setStatus((int) 3);
                }
            }
            else if (mark1.getTheory1() >= 5 && mark1.getSkill1() < 5) {
                if (mark1.getSkill2() >= 5) {
                    mark1.setStatus((int) 1);
                } else {
                    mark1.setStatus((int) 3);
                }
            }
            else if (mark1.getTheory1() < 5 && mark1.getSkill1() >= 5) {
                if (mark1.getTheory2() >= 5) {
                    mark1.setStatus((int) 1);
                } else {
                    mark1.setStatus((int) 3);
                }
            }
            Mark result = markRepository.save(mark1);
            MarkDTO markDTO1 = toMarkDto.getDestination(result);
            responseDto.setObject(markDTO1);
        }
        return responseDto;
    }

    public ResponseDto search(SearchReqDto reqDto) {
         ResponseDto responseDto = new ResponseDto();
        // D??ng h??m search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Mark> marks = markRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkDTO> markDTOS = new ArrayList<>();

        for (var mark : marks) {
            MarkDTO markDTO = toMarkDto.getDestination(mark);

            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            MarkRetest markRetest = markRetestRepository.findByStudentIdAndSubjectId(student.getId(), subject.getId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setStudentCode(student.getStudentCode());
            markDTO.setSubjectName(subject.getName());
            if (markRetest == null) {
                markDTO.setRetestTheory(null);
                markDTO.setRetestSkill(null);
            } else {
                markDTO.setRetestTheory(markRetest.getRetestTheory());
                markDTO.setRetestSkill(markRetest.getRetestSkill());
                if (markRetest.getRetestTheory() < 5 || markRetest.getRetestSkill() < 5 ) {
                    markDTO.setStatus(3);
                } else if (markRetest.getRetestTheory() >= 5 && markRetest.getRetestSkill() >= 5) {
                    markDTO.setStatus(1);
                }
            }
            markDTOS.add(markDTO);
        }
//        ThongSoThongKeDTO thongSoThongKeDTO = new ThongSoThongKeDTO();
//        for (var thongKe : markDTOS) {
//            if (thongKe.getStatus() == 1) {
//                    thongSoThongKeDTO.setSumQuaMon(thongSoThongKeDTO.getSumQuaMon()+1);
//            }
//            if (thongKe.getStatus() == 2) {
//                    thongSoThongKeDTO.setSumThiLai(thongSoThongKeDTO.getSumThiLai()+1);
//            }
//            if (thongKe.getStatus() == 3) {
//                    thongSoThongKeDTO.setSumHocLai(thongSoThongKeDTO.getSumHocLai()+1);
//            }
//        }
//        markDTOS.stream().map(markDTO -> {
//                markDTO.setThongSoThongKeDTO(thongSoThongKeDTO);
//            return markDTO;
//        }).collect(Collectors.toList());
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), markDTOS));
        return responseDto;
    }

    public ResponseDto searchThongKe(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // D??ng h??m search (hero)
        PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
                by(getOrders(reqDto.getSorts(), DEFAULT_PROP)));
        Page<Mark> marks = markRepository.findAll(createSpec(reqDto.getQuery()), pageRequest);
        // entity -> dto
        List<MarkDTO> markDTOS = new ArrayList<>();
        for (var mark : marks) {
            MarkDTO markDTO = toMarkDto.getDestination(mark);
            Student student = studentRepository.findById(markDTO.getStudentId()).orElse(null);
            CtdtSubjectClass ctdtSubjectClass = ctdtSubjectClassRepository.findById(markDTO.getCtdtSubjectClassId()).orElse(null);
            Subject subject = subjectRepository.findByCtdtSubjectClassIdOne(markDTO.getCtdtSubjectClassId());
            markDTO.setCtdtSubjectClassName(ctdtSubjectClass.getName());
            markDTO.setStudentName(student.getFullName());
            markDTO.setSubjectName(subject.getName());
            markDTOS.add(markDTO);
        }
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), markDTOS));
        return responseDto;
    }

    public ResponseDto searchMarkBy(Integer pageIndex, Integer pageSize, String search, Long ctdtSubjectClassId, Integer status, Long subjectId) {
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
            sql += "S-subjectName=L\"" + search + "\", OR-S-studentName=L\"" + search + "\"";
        }
        if (status != null) {
            sql += ",N-status=\"" + status + "\"";
        }
        if (subjectId != null) {
            sql += ",N-subjectId=\"" + subjectId + "\"";
        }
        if (ctdtSubjectClassId != null) {
            sql += ",N-ctdtSubjectClassId=\"" + ctdtSubjectClassId + "\"";
        }

        searchReqDto.setQuery(sql);
        searchReqDto.setPageSize(pageSize);
        searchReqDto.setPageIndex(pageIndex);
        responseDto = markService.search(searchReqDto);
        return responseDto;
    }

    public ResponseDto delete(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Mark> mark = markRepository.findById(id);
        if (mark.isPresent()) {
            markRepository.deleteById(id);
            responseDto.setObject(id);
        }
        return responseDto;
    }

}
