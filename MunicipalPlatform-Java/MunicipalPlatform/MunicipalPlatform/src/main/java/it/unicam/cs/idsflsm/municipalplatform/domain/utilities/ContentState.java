package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import lombok.Getter;

@Getter
public enum ContentState {
    REMOVABLE(1),
    VALIDABLE(2),
    UPLOADABLE(3),
    UPLOADED(4);
    private final int value;
    private ContentState(int value) {
        this.value = value;
    }
    // Method to convert string to enum
    public static ContentState fromString(String text) {
        for (ContentState state : ContentState.values()) {
            if (state.name().equalsIgnoreCase(text)) {
                return state;
            }
        }
        throw new InvalidContentStateException(text);
    }
    // Method to convert enum to string
    @Override
    public String toString() {
        return this.name();
    }
}
