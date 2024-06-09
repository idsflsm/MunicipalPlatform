package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import lombok.Getter;

/**
 * Represents the possible states that a content on the platform may have
 */
@Getter
public enum ContentState {
    /**
     * Represents a content that can be permanently deleted
     */
    REMOVABLE(1),
    /**
     * Represents a content that needs appropriate validation
     */
    VALIDABLE(2),
    /**
     * Represents a content that can be uploaded (made visible) on the platform
     */
    UPLOADABLE(3),
    /**
     * Represents a content already loaded (made visible) on the platform
     */
    UPLOADED(4);
    private final int value;
    private ContentState(int value) {
        this.value = value;
    }

    /**
     * Method for converting a string to a ContentState value
     * @param text string value to be converted
     * @return the value of ContentState associated with the string
     * if the latter is not null, null otherwise
     * @throws InvalidContentStateException if string is not null
     * but cannot be converted to any ContentState value
     */
    public static ContentState fromString(String text) throws InvalidContentStateException {
        if (text != null) {
            for (ContentState state : ContentState.values()) {
                if (state.name().equalsIgnoreCase(text)) {
                    return state;
                }
            }
            throw new InvalidContentStateException(text);
        }
        return null;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
