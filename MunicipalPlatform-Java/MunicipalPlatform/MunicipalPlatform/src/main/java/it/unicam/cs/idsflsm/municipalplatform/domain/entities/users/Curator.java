package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

public class Curator extends AuthorizedContributor {
    public Curator(String username, String password, String name, String surname) {
        super(username, password, name, surname);
    }
}
