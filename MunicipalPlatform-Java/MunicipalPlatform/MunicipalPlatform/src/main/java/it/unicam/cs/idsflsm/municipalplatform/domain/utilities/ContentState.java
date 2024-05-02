package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
public enum ContentState {
    DISPOSABLE(1),
    VALIDABLE(2),
    UPLOADABLE(3),
    UPLOADED(4);
    private final int value;
    private ContentState(int value) {
        this.value = value;
    }
}
