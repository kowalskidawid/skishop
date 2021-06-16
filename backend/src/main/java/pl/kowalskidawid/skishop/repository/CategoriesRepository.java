package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.Category;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    @Query("select c from categories c where c.categoryName like ?1")
    List<Category> search(String query);
}
