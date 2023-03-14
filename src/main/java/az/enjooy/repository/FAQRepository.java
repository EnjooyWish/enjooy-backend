package az.enjooy.repository;

import az.enjooy.model.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> getAllByIsActive(Boolean isActive);
}
