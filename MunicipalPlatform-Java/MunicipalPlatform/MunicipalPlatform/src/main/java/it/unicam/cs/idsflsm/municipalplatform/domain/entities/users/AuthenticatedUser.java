package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

public abstract class AuthenticatedUser extends AnonymousUser implements IAuthenticatedUser {
    protected String username;
    protected String password;
    protected String name;
    protected String surname;
}
