package com.ssafy.matchup.mz.article.controller;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.mz.article.dto.MzArticleDto;
import com.ssafy.matchup.mz.article.dto.SimpleMzArticleDto;
import com.ssafy.matchup.mz.article.dto.request.WriteMzArticleRequestDto;
import com.ssafy.matchup.mz.article.service.MzArticleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mz/articles")
@RequiredArgsConstructor
public class MzArticleController {

    private final JwtTokenUtil jwtTokenUtil;
    private final MzArticleService mzArticleService;

//    @Operation(summary = "게시글 목록", description = "전체 게시글을 페이지 별로 조회하는 API입니다.")
//    @GetMapping
//    ResponseEntity<Page<SimpleMzArticleDto>> articleList(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
//                                                         @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria) {
//        return new ResponseEntity<>(mzArticleService.listMzArticle(page, criteria), HttpStatus.OK);
//    }
//
//    @Operation(summary = "내 게시글 조회", description = "내가 쓴 게시글을 조회하는 API 입니다.")
//    @GetMapping("/my")
//    ResponseEntity<Page<SimpleMzArticleDto>> myArticleList(HttpServletRequest request,
//                                                           @RequestParam(required = false, defaultValue = "0", value = "page") int page,
//                                                           @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria) {
//        ;
//        return new ResponseEntity<>(mzArticleService.listMyMzArticle(jwtTokenUtil.getUserId(request), page, criteria), HttpStatus.OK);
//    }

    @Operation(summary = "게시글 목록", description = "전체 게시글을 페이지 별로 조회하는 API입니다.")
    @GetMapping
    ResponseEntity<ListDto<SimpleMzArticleDto>> articleList() {
        return new ResponseEntity<>(mzArticleService.listMzArticle(), HttpStatus.OK);
    }

    @Operation(summary = "내 게시글 조회", description = "내가 쓴 게시글을 조회하는 API 입니다.")
    @GetMapping("/my")
    ResponseEntity<ListDto<SimpleMzArticleDto>> myArticleList(HttpServletRequest request) {
        ;
        return new ResponseEntity<>(mzArticleService.listMyMzArticle(jwtTokenUtil.getUserId(request)), HttpStatus.OK);
    }

    @Operation(summary = "게시글 등록", description = "게시글을 등록하는 API입니다.")
    @PostMapping
    ResponseEntity<Void> mzArticleRegist(HttpServletRequest request, @RequestBody WriteMzArticleRequestDto writeMzArticleRequestDto) {
        // TODO : thumbnail 가져오기, url 만드는 로직 필요
        mzArticleService.addMzArticle(jwtTokenUtil.getUserId(request), writeMzArticleRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회하는 API입니다.")
    @GetMapping("/{article-id}")
    ResponseEntity<MzArticleDto> mzArticleDetails(@PathVariable("article-id") Long articleId) {
        return new ResponseEntity<>(mzArticleService.detailMzArticle(articleId), HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정하는 API입니다.")
    @PatchMapping("/{article-id}")
    ResponseEntity<Void> mzArticleModify(HttpServletRequest request, @PathVariable("article-id") Long articleId, @RequestBody WriteMzArticleRequestDto writeMzArticleRequestDto) {
        mzArticleService.updateMzArticle(jwtTokenUtil.getUserId(request), articleId, writeMzArticleRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제하는 API입니다.")
    @DeleteMapping("/{article-id}")
    ResponseEntity<Void> mzArticleRemove(HttpServletRequest request, @PathVariable("article-id") Long articleId) {
        mzArticleService.deleteMzArticle(articleId, jwtTokenUtil.getUserId(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
