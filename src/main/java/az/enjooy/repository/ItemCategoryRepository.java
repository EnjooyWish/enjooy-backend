package az.enjooy.repository;

import az.enjooy.model.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    List<ItemCategory> findAllByParentIsNull();
    List<ItemCategory> findByParentId(Long id);
}
