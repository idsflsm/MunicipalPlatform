package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import lombok.Getter;
@Getter
public enum ContestResult {
    WINNER(1),
    LOSER(2);
    private final int value;
    private ContestResult(int value) {
        this.value = value;
    }
}
