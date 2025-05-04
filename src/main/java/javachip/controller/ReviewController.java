/*
파일명 : ReviewController.java
파일설명 : 리뷰 등록 요청을 처리하는 REST 컨트롤러
작성자 : 김민하
작성일 : 2025-05-04
*/
package javachip.controller;

import javachip.dto.ReviewDto;
import javachip.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewDto dto) {
        ReviewDto response = reviewService.createReview(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviewsByProductId(@RequestParam Long productId) {
        List<ReviewDto> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}

