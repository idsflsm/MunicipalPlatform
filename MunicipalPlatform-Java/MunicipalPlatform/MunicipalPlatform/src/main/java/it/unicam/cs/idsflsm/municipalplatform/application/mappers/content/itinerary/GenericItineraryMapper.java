package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;

import java.util.List;
import java.util.stream.Collectors;
public class GenericItineraryMapper {
    public static ItineraryDto toDto(Itinerary itinerary, boolean includeRelativeEntities) {
        if (itinerary != null) {
            if (itinerary instanceof PendingItinerary pendingItinerary) {
                return PendingItineraryMapper.toDto(pendingItinerary, includeRelativeEntities);
            } else if (itinerary instanceof AuthorizedItinerary authorizedItinerary) {
                return AuthorizedItineraryMapper.toDto(authorizedItinerary, includeRelativeEntities);
            }
        }
        return null;
    }
    public static Itinerary toEntity(ItineraryDto itineraryDto, boolean includeRelativeEntities) {
        if (itineraryDto != null) {
            if (itineraryDto instanceof PendingItineraryDto pendingItineraryDto) {
                return PendingItineraryMapper.toEntity(pendingItineraryDto, includeRelativeEntities);
            } else if (itineraryDto instanceof AuthorizedItineraryDto authorizedItineraryDto) {
                return AuthorizedItineraryMapper.toEntity(authorizedItineraryDto, includeRelativeEntities);
            }
        }
        return null;
    }
    public static List<ItineraryDto> toDto(List<Itinerary> itineraries, boolean includeRelativeEntities) {
        if (itineraries != null) {
            return itineraries.stream()
                    .map(itinerary -> GenericItineraryMapper.toDto(itinerary, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<Itinerary> toEntity(List<ItineraryDto> itineraryDtos, boolean includeRelativeEntities) {
        if (itineraryDtos != null) {
            return itineraryDtos.stream()
                    .map(itineraryDto -> GenericItineraryMapper.toEntity(itineraryDto, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}