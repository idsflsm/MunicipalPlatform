package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

import java.util.UUID;

public class AuthenticatedTourist extends AuthenticatedUser {

    public AuthenticatedTourist(String username, String password, String name, String surname) {
        super.username = username;
        super.password = password;
        super.name = name;
        super.surname = surname;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getSurname() {
        return "";
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setSurname(String surname) {

    }

}
