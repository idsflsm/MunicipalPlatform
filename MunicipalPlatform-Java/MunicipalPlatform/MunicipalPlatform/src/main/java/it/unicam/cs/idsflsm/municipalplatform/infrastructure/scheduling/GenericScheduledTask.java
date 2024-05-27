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
public abstract class GenericScheduledTask<T> {
    private final GenericRepository<T, UUID> _genericRepository;
    @Getter
    @Setter
    private String methodName = "";
    public GenericScheduledTask(GenericRepository<T, UUID> _genericRepository) {
        this._genericRepository = _genericRepository;
    }
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void deleteEntities() {
        deleteEntities(this.methodName);
    }
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
