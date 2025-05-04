/*
파일명 : ReviewServiceImpl.java
파일설명 : 리뷰 등록 비즈니스 로직 구현체
작성자 : 김민하
작성일 : 2025-05-04
설명 : 주문 항목 유효성 검증, 리뷰 저장, 리뷰 이미지 엔티티 변환 등을 처리함
*/
package javachip.service.impl;

import javachip.dto.ReviewDto;
import javachip.entity.OrderItem;
import javachip.entity.Review;
import javachip.entity.ReviewImage;
import javachip.repository.OrderItemRepository;
import javachip.repository.ReviewRepository;
import javachip.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ReviewDto createReview(ReviewDto dto) {
        // 주문 항목 유효성 검사
        OrderItem orderItem = orderItemRepository.findById(dto.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("주문 항목을 찾을 수 없습니다."));

        if (orderItem.isReviewed()) {
            throw new IllegalStateException("이미 리뷰가 작성된 주문입니다.");
        }

        // Review 객체 생성
        Review review = new Review();
        review.setOrderItem(orderItem);
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());
        review.setCreatedAt(LocalDateTime.now());

        // 이미지 변환
        List<ReviewImage> imageList = dto.getImageUrls().stream()
                .map(url -> {
                    ReviewImage img = new ReviewImage();
                    img.setImageUrl(url);
                    img.setReview(review);
                    return img;
                })
                .collect(Collectors.toList());

        review.setImageList(imageList);

        // 저장
        Review saved = reviewRepository.save(review);
        orderItem.setReviewed(true);

        // 응답
        dto.setId(saved.getId());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setUserId(orderItem.getUserId()); // 리뷰 작성자 정보 포함
        return dto;

    }

    // ✅ 별도 조회 메서드로 분리
    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByOrderItem_Product_IdOrderByCreatedAtDesc(productId);
        return reviews.stream().map(review -> {
            ReviewDto dto = new ReviewDto();
            dto.setId(review.getId());
            dto.setRating(review.getRating());
            dto.setContent(review.getContent());
            dto.setCreatedAt(review.getCreatedAt());
            dto.setImageUrls(
                    review.getImageList().stream()
                            .map(ReviewImage::getImageUrl)
                            .collect(Collectors.toList())
            );
            dto.setUserId(review.getOrderItem().getUserId());
            return dto;
        }).collect(Collectors.toList());
    }
}
