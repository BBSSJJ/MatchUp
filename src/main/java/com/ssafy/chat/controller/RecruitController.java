package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ListDataDto;
import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recruits")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping
    public ResponseEntity<?> showRecruits() {

        List<RecruitDto> list = recruitService.findRecruits();

        return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
    }
}
