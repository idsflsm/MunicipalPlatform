package it.unicam.cs.idsflsm.municipalplatform.application.mappers.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;

import java.util.List;
import java.util.stream.Collectors;
public class PendingItineraryMapper {
    public static PendingItineraryDto toDto(PendingItinerary itinerary) {
        if (itinerary != null) {
            return new PendingItineraryDto(
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
    public static PendingItinerary toEntity(PendingItineraryDto itineraryDto) {
        if (itineraryDto != null) {
            return new PendingItinerary(
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
    public static List<PendingItineraryDto> toDto(List<PendingItinerary> itineraries) {
        if (itineraries != null) {
            return itineraries.stream().map(PendingItineraryMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<PendingItinerary> toEntity(List<PendingItineraryDto> itineraryDtos) {
        if (itineraryDtos != null) {
            return itineraryDtos.stream().map(PendingItineraryMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
