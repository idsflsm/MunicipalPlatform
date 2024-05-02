package it.unicam.cs.idsflsm.municipalplatform.api.controllers.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.services.poi.POIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/pois")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIController {
    private final IPOIService _poiService;
}
