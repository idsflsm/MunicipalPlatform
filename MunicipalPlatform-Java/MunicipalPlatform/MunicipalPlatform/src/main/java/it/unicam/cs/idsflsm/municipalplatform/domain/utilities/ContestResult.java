package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContestResultException;
import lombok.Getter;
@Getter
public enum ContestResult {
    WINNER(1),
    LOSER(2);
    private final int value;
    private ContestResult(int value) {
        this.value = value;
    }
    public static ContestResult fromString(String text) {
        for (ContestResult result : ContestResult.values()) {
            if (result.name().equalsIgnoreCase(text)) {
                return result;
            }
        }
        throw new InvalidContestResultException(text);
    }
    @Override
    public String toString() {
        return this.name();
    }
}
