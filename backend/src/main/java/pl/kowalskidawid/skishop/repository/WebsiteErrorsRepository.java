package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.WebsiteError;

@Repository
public interface WebsiteErrorsRepository extends JpaRepository<WebsiteError, Integer> {
}
