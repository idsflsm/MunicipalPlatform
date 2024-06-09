package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContestResultException;
import lombok.Getter;
/**
 * Represents the possible results of a contest contribution
 */
@Getter
public enum ContestResult {
    /**
     * Represents a winner contribution
     */
    WINNER(1),
    /**
     * Represents a loser contribution
     */
    LOSER(2);
    private final int value;
    private ContestResult(int value) {
        this.value = value;
    }

    /**
     * Method for converting a string to a ContestResult value
     * @param text string value to be converted
     * @return the value of ContestResult associated with the string
     * if the latter is not null, null otherwise
     * @throws InvalidContestResultException if string is not null
     * but cannot be converted to any ContestResult value
     */
    public static ContestResult fromString(String text) throws InvalidContestResultException {
        if (text != null) {
            for (ContestResult result : ContestResult.values()) {
                if (result.name().equalsIgnoreCase(text)) {
                    return result;
                }
            }
            throw new InvalidContestResultException(text);
        }
        return null;
    }
    @Override
    public String toString() {
        return this.name();
    }
}
