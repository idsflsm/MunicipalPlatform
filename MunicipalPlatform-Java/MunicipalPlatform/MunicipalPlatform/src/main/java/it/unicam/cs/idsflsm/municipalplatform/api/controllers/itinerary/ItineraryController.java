package it.unicam.cs.idsflsm.municipalplatform.api.controllers.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary.ItineraryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/itineraries")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryController {
    private final IItineraryService _itineraryService;
}
