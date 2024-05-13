package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@NoRepositoryBean
public interface GenericRepository<T, UUID> extends JpaRepository<T, UUID> {
    default List<T> findByPredicate(Predicate<T> predicate) {
        List<T> result = findAll().stream().filter(predicate).toList();
        if (!result.isEmpty()) return result; else return null;
    }
}
