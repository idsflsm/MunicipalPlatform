package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

public interface IAuthenticatedUser extends IAnonymousUser {
    String getUsername();
    String getPassword();
    String getName();
    String getSurname();
    void setUsername(String username);
    void setPassword(String password);
    void setName(String name);
    void setSurname(String surname);
}
