package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import java.util.List;
import java.util.stream.Collectors;
public class AuthorizedItineraryMapper {
    public static AuthorizedItineraryDto toDto(AuthorizedItinerary itinerary) {
        if (itinerary != null) {
            return new AuthorizedItineraryDto(
                    itinerary.getId(),
                    itinerary.getName(),
                    itinerary.getCoordinates(),
                    itinerary.getDescription(),
                    itinerary.getAuthor(),
                    itinerary.getCreationDate(),
                    itinerary.getExpiryDate(),
                    itinerary.getState(),
                    itinerary.getItineraryPois(),
                    itinerary.getTourists(),
                    itinerary.getAttachments()
            );
        } else {
            return null;
        }
    }
    public static AuthorizedItinerary toEntity(AuthorizedItineraryDto itineraryDto) {
        if (itineraryDto != null) {
            return new AuthorizedItinerary(
                    itineraryDto.getId(),
                    itineraryDto.getName(),
                    itineraryDto.getCoordinates(),
                    itineraryDto.getDescription(),
                    itineraryDto.getAuthor(),
                    itineraryDto.getCreationDate(),
                    itineraryDto.getExpiryDate(),
                    itineraryDto.getState(),
                    itineraryDto.getItineraryPois(),
                    itineraryDto.getTourists(),
                    itineraryDto.getAttachments()
            );
        } else {
            return null;
        }
    }
    public static List<AuthorizedItineraryDto> toDto(List<AuthorizedItinerary> itineraries) {
        if (itineraries != null) {
            return itineraries.stream().map(AuthorizedItineraryMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthorizedItinerary> toEntity(List<AuthorizedItineraryDto> itineraryDtos) {
        if (itineraryDtos != null) {
            return itineraryDtos.stream().map(AuthorizedItineraryMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
