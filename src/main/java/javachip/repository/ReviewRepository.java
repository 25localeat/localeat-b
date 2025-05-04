package javachip.repository;

import javachip.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 최신순 정렬
    List<Review> findAllByOrderByCreatedAtDesc();

    // 특정 주문 항목에 대한 리뷰 조회
    Review findByOrderItem_Id(Long orderItemId);

    List<Review> findByOrderItem_Product_IdOrderByCreatedAtDesc(Long productId);

}
