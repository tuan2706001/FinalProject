package com.java.project3.service;

import com.googlecode.jmapper.JMapper;
import com.java.project3.domain.Grade;
import com.java.project3.domain.Major;
import com.java.project3.domain.Mark;
import com.java.project3.domain.Subject;
import com.java.project3.dto.MarkDTO;
import com.java.project3.dto.SubjectDTO;
import com.java.project3.dto.base.ResponseDto;
import com.java.project3.repository.GradeRepository;
import com.java.project3.repository.MajorRepository;
import com.java.project3.repository.MarkRepository;
import com.java.project3.repository.SubjectRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.java.project3.utils.SearchUtil.prepareResponseForSearch;

@Service
public class ThongKeSevice {
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    MarkRepository markRepository;


    JMapper<SubjectDTO, Subject> toSubjectDto;
    JMapper<Subject, SubjectDTO> toSubject;
    JMapper<MarkDTO, Mark> toMarkDto;
    JMapper<Mark, MarkDTO> toMark;


    public ThongKeSevice() {
        this.toSubjectDto = new JMapper<>(SubjectDTO.class, Subject.class);
        this.toSubject = new JMapper<>(Subject.class, SubjectDTO.class);
        this.toMarkDto = new JMapper<>(MarkDTO.class, Mark.class);
        this.toMark = new JMapper<>(Mark.class, MarkDTO.class);
    }

    public ResponseDto findSubjectByMajorId (Integer pageIndex, Integer pageSize, Long gradeId) {
        ResponseDto responseDto = new ResponseDto();
        Grade grade = gradeRepository.findById(gradeId).orElse(null);
        Major major = majorRepository.findById(grade.getMajorId()).orElse(null);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Subject> subjects = subjectRepository.findByMajorId(major, pageable);
        List<SubjectDTO> listDto = new ArrayList<>();
        for (var item : subjects) {
            listDto.add(toSubjectDto.getDestination(item));
        }
        responseDto.setObject(prepareResponseForSearch(subjects.getTotalPages(), subjects.getNumber(), subjects.getTotalElements(), listDto));

        return responseDto;
    }

    public ResponseDto findMarkBySubjectIdAndGradeId (Integer pageIndex, Integer pageSize, Long subjectId, Long gradeId) {
        ResponseDto responseDto = new ResponseDto();
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        Grade grade = gradeRepository.findById(gradeId).orElse(null);
//        Major major = majorRepository.findById(grade.getMajorId()).orElse(null);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Mark> marks = markRepository.findBySubjectIdAndGradeId(subject, grade, pageable);
        List<MarkDTO> listDto = new ArrayList<>();
        for (var item : marks) {
            listDto.add(toMarkDto.getDestination(item));
        }
        responseDto.setObject(prepareResponseForSearch(marks.getTotalPages(), marks.getNumber(), marks.getTotalElements(), listDto));

        return responseDto;
    }



}
