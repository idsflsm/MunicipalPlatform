package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.UUID;
/**
 * Generic abstract class for scheduled task that operates on a generic type T
 * @param <T> the type of entity this task operates on
 */
@RequiredArgsConstructor
public abstract class GenericScheduledTask<T> {
    /**
     * The repository for T entities
     */
    private final GenericRepository<T, UUID> _genericRepository;
    /**
     * The name of repository method to be invoked
     */
    @Getter
    @Setter
    private String methodName = "";
    /**
     * Scheduled task method that deletes entities every 24 hours
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void deleteEntities() {
        deleteEntities(this.methodName);
    }
    /**
     * Method that deletes entities based on the specified method name
     * @param methodName the name of repository method to be invoked
     */
    private void deleteEntities(String methodName) {
        try {
            Date now = Date.toDate(LocalDate.now());
            Method method = _genericRepository.getClass().getDeclaredMethod(methodName, Date.class);
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            Iterable<T> entities = (Iterable<T>) method.invoke(_genericRepository, now);
            _genericRepository.deleteAll(entities);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
    }
}
