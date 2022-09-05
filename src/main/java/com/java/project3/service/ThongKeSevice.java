package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.*;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.ThongSoThongKeDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.dto.base.SearchReqDto;
import com.java.project3.repository.*;
import com.java.project3.utils.PageUltil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.java.project3.constant.Constants.DEFAULT_PROP;
import static com.java.project3.utils.SearchUtil.*;
import static org.springframework.data.domain.Sort.by;

@Service
public class ThongKeSevice {
    @Autowired
    CourseClassRepository courseClassRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    MarkRepository markRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CtdtSubjectClassRepository ctdtSubjectClassRepository;
    @Autowired
    MarkRetestRepository markRetestRepository;

    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public ThongKeSevice() {
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }



    public ResponseDto search(SearchReqDto reqDto) {
        ResponseDto responseDto = new ResponseDto();
        // Dùng hàm search (hero)
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
        ThongSoThongKeDTO thongSoThongKeDTO = new ThongSoThongKeDTO();
        for (var thongKe : markDTOS) {
            if (thongKe.getStatus() == 1) {
                thongSoThongKeDTO.setSumQuaMon(thongSoThongKeDTO.getSumQuaMon()+1);
            }
            if (thongKe.getStatus() == 2) {
                thongSoThongKeDTO.setSumThiLai(thongSoThongKeDTO.getSumThiLai()+1);
            }
            if (thongKe.getStatus() == 3) {
                thongSoThongKeDTO.setSumHocLai(thongSoThongKeDTO.getSumHocLai()+1);
            }
        }
        thongSoThongKeDTO.setMarkDTOS(markDTOS);
//        markDTOS.stream().map(markDTO -> {
//            markDTO.setThongSoThongKeDTO(thongSoThongKeDTO);
//            return markDTO;
//        }).collect(Collectors.toList());
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), thongSoThongKeDTO));
        return responseDto;
    }

    public ResponseDto findByMark(Integer pageIndex, Integer pageSize, String seach, Long ctdtSubjectClassId) {
        ResponseDto responseDto = new ResponseDto();
        SearchReqDto searchReqDto = new SearchReqDto();
        List<String> sort = new ArrayList<>();
        sort.add("id");
        searchReqDto.setSorts(sort);
        searchReqDto.setQuery("");
        String sql = "";
//        if (seach != null) {
//            sql += id + "S-studentName=L\"" + seach + "\"";
//        }
//        if (ctdtSubjectClassId != null) {
//            sql += id + "N-ctdtSubjectClassId=\"" + ctdtSubjectClassId + "\"";
//        }

        searchReqDto.setQuery(sql);
        com.java.project3.dto.base.Page page = PageUltil.setDefault(pageIndex, pageSize);
        searchReqDto.setPageSize(page.getPageSize());
        searchReqDto.setPageIndex(page.getCurrentPage() - 1);
        responseDto = search(searchReqDto);
        return responseDto;
    }



}
