package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public class PendingPOI extends POI {

    public PendingPOI(String name, Coordinates coordinates, Date expiryDate, ContentStatus state) {

    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public Date getExpiryDate() {
        return null;
    }

    @Override
    public ContentStatus getState() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setCoordinates(Coordinates coordinates) {

    }

    @Override
    public void setExpiryDate(Date expiryDate) {

    }

    @Override
    public void setState(ContentStatus state) {

    }
}
