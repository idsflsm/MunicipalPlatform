package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

public enum ContentStatus {
    PENDINGSTATE(1),
    READYSTATE(2),
    DELETINGSTATE(3);

    private final int value;
    private ContentStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
