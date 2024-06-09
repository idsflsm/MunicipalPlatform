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
/**
 * Utility generic class for mapping between Itinerary and ItineraryDto
 */
public class GenericItineraryMapper {
    /**
     * Converts an Itinerary entity to an Itinerary DTO
     * @param itinerary the itinerary entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding ItineraryDto if itinerary in the parameter is not null, null otherwise
     */
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
    /**
     * Converts an Itinerary DTO to an Itinerary entity
     * @param itineraryDto the itinerary DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Itinerary if the itineraryDto in the parameter is not null, null otherwise
     */
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
    /**
     * Converts a list of Itinerary entities to a list of Itinerary DTOs
     * @param itineraries the list of Itinerary entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of ItineraryDto if the list in the parameter is not null, null otherwise
     */
    public static List<ItineraryDto> toDto(List<Itinerary> itineraries, boolean includeRelativeEntities) {
        if (itineraries != null) {
            return itineraries.stream()
                    .map(itinerary -> GenericItineraryMapper.toDto(itinerary, includeRelativeEntities))
                    .collect(Collectors.toList());
        }
        return null;
    }
    /**
     * Converts a list of Itinerary DTOs to a list of Itinerary entities
     * @param itineraryDtos the list of Itinerary DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of Itinerary if the list in the parameter is not null, null otherwise
     */
    public static List<Itinerary> toEntity(List<ItineraryDto> itineraryDtos, boolean includeRelativeEntities) {
        if (itineraryDtos != null) {
            return itineraryDtos.stream()
                    .map(itineraryDto -> GenericItineraryMapper.toEntity(itineraryDto, includeRelativeEntities))
                    .collect(Collectors.toList());
        }
        return null;
    }
}