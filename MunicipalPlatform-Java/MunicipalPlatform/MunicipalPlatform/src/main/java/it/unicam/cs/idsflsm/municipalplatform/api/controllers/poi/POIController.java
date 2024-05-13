package it.unicam.cs.idsflsm.municipalplatform.api.controllers.poi;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.AddPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.ModifyPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.UpdatePOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@Validated
@RequestMapping("/api/pois")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIController {
    private final IPOIService _poiService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingPOIs
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<POI> criterias = POICriteria.isPendingPoi()
                    .and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<PendingPOIDto> pendingPoiDtos =
                    _poiService.getAllPendingPOIs(Optional.of(criterias));
            return new ResponseEntity<>(pendingPoiDtos, HttpStatus.OK);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedPOIs
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<POI> criterias = POICriteria.isAuthorizedPoi()
                    .and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<AuthorizedPOIDto> authorizedPoiDtos =
                    _poiService.getAllAuthorizedPOIs(Optional.of(criterias));
            return new ResponseEntity<>(authorizedPoiDtos, HttpStatus.OK);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<POI> getAllCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
        Coordinates c = Coordinates.fromStrings(latitude, longitude);
        Date cd = (creationDate != null) ? Date.fromString(creationDate) : null;
        Date ed = (expiryDate != null) ? Date.fromString(expiryDate) : null;
        ContentState cs = (state != null) ? ContentState.fromString(state) : null;
        return POICriteria.criteriaBuilder(
                POICriteria.hasName(name),
                POICriteria.hasCoordinates(c),
                POICriteria.hasDescription(description),
                POICriteria.hasAuthor(author),
                POICriteria.hasCreationDate(cd),
                POICriteria.hasExpiryDate(ed),
                POICriteria.hasState(cs)
        );
    }
    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingPOI(@PathVariable("id") UUID id) {
        PendingPOIDto pendingPoiDto = _poiService.getPendingPOIById(id);
        if (pendingPoiDto != null) {
            return new ResponseEntity<>(pendingPoiDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedPOI(@PathVariable("id") UUID id) {
        AuthorizedPOIDto authorizedPoiDto = _poiService.getAuthorizedPOIById(id);
        if (authorizedPoiDto != null) {
            return new ResponseEntity<>(authorizedPoiDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/pending")
    public ResponseEntity<?> addPendingPoi(@Valid @RequestBody AddPOIRequest request) {
        try {
            PendingPOIDto pendingPoiDto = new PendingPOIDto();
            boolean result = _poiService.addPendingPOI(modifyPoiConfiguration(pendingPoiDto, request, null));
            if (result) {
                return new ResponseEntity<>(pendingPoiDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/authorized")
    public ResponseEntity<?> addAuthorizedPoi(@Valid @RequestBody AddPOIRequest request) {
        try {
            AuthorizedPOIDto authorizedPoiDto = new AuthorizedPOIDto();
            boolean result = _poiService.addAuthorizedPOI(modifyPoiConfiguration(authorizedPoiDto, request, null));
            if (result) {
                return new ResponseEntity<>(authorizedPoiDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/pending/{id}")
    public ResponseEntity<?> deletePendingPoi(@PathVariable("id") UUID id) {
        boolean result = _poiService.deletePendingPOIById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/authorized/{id}")
    public ResponseEntity<?> deleteAuthorizedPoi(@PathVariable("id") UUID id) {
        boolean result = _poiService.deleteAuthorizedPOIById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingPoi(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePOIRequest request) {
        PendingPOIDto existingPendingPoi = _poiService.getPendingPOIById(id);
        if (existingPendingPoi != null) {
            try {
                boolean result = _poiService.updatePendingPOI(modifyPoiConfiguration(existingPendingPoi, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingPendingPoi, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedPoi(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePOIRequest request) {
        AuthorizedPOIDto existingAuthorizedPoi = _poiService.getAuthorizedPOIById(id);
        if (existingAuthorizedPoi != null) {
            try {
                boolean result = _poiService.updateAuthorizedPOI(modifyPoiConfiguration(existingAuthorizedPoi, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingAuthorizedPoi, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private <T extends POIDto, S extends ModifyPOIRequest> T modifyPoiConfiguration(T poiDto, S request, UUID id) {
        poiDto.setId((id != null) ? id : UUID.randomUUID());
        poiDto.setName(request.getName());
        poiDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        poiDto.setDescription(request.getDescription());
        poiDto.setAuthor(request.getAuthor());
        LocalDate localDate = LocalDate.now();
        poiDto.setCreationDate(new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear()));
        poiDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return poiDto;
    }
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingPOI(@PathVariable("id") UUID id, @Valid @RequestBody boolean validate) {
        PendingPOIDto poiDto = _poiService.getPendingPOIById(id);
        Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id));
        if (poiDto != null) {
            boolean result = _poiService.validatePendingPOI(poiDto, predicate, validate);
            if (result) {
                return new ResponseEntity<>(poiDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/pending/save/{id}")
    public ResponseEntity<?> savePendingPoi(@PathVariable UUID id, UUID idAuthenticatedUser) {
        boolean result = _poiService.savePendingPOI(id, idAuthenticatedUser);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/authorized/save/{id}")
    public ResponseEntity<?> saveAuthorizedPoi(@PathVariable UUID id, UUID idAuthenticatedUser) {
        boolean result = _poiService.saveAuthorizedPOI(id, idAuthenticatedUser);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/attachments/pending/{id}")
    public ResponseEntity<?> addPendingAttachment(@PathVariable("id") UUID id, @Valid @RequestBody PendingAttachmentDto attachmentDto) {
        Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id));
        boolean result = _poiService.addPendingAttachment(attachmentDto, predicate);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/attachments/authorized/{id}")
    public ResponseEntity<?> addAuthorizedAttachment(@PathVariable("id") UUID id, @Valid @RequestBody AuthorizedAttachmentDto authorizedAttachmentDto) {
        Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id));
        boolean result = _poiService.addAuthorizedAttachment(authorizedAttachmentDto, predicate);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
