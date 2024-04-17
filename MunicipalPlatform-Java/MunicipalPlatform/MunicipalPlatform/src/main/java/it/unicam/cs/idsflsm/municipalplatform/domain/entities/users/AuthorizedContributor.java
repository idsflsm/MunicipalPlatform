package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

public class AuthorizedContributor extends AuthenticatedTourist {
    public AuthorizedContributor(String username, String password, String name, String surname) {
        super(username, password, name, surname);
    }
}
