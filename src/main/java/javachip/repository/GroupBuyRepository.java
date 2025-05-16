package javachip.repository;

import javachip.entity.GroupBuy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBuyRepository extends JpaRepository<GroupBuy, Long> {
    List<GroupBuy> findByProduct_Id(Long productId);
}
