/*
파일명 : ReviewDto.java
파일설명 : 리뷰 작성 시 요청과 응답 처리
작성자 : 김민하
작성일 : 2025-05-03
*/
package javachip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    // 추가
    private String userId;  // 리뷰 작성자 ID

    // 요청용 필드
    private Long orderItemId;         // 주문 항목 ID
    private int rating;               // 별점
    private String content;           // 리뷰 내용
    private List<String> imageUrls;   // 이미지 URL 리스트

    // 응답용 필드
    private Long id;                  // 저장된 리뷰 ID
    private LocalDateTime createdAt;  // 등록일
}
