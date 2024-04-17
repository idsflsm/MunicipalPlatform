package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

public class Contributor extends AuthenticatedTourist {
    public Contributor(String username, String password, String name, String surname) {
        super(username, password, name, surname);
    }
}
