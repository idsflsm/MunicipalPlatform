package it.unicam.cs.idsflsm.municipalplatform.application.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest.IContestService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContributionCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.authenticated.AuthenticatedUserCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContributionMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContributionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * Service class for both the ContestRepository and the ContributionRepository.
 * It provides methods to manipulate persistent contests and contributions in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ContestService implements IContestService {
    /**
     * The repository for Contest entity
     */
    private final IContestRepository _contestRepository;
    /**
     * The repository for Contribution entity
     */
    private final IContributionRepository _contributionRepository;
    /**
     * The repository for Itinerary entity
     */
    private final IItineraryRepository _itineraryRepository;
    /**
     * The repository for POI entity
     */
    private final IPOIRepository _poiRepository;
    @Override
    public void saveInRepository(Contest contest) {
        _contestRepository.save(contest);
    }
    @Override
    public void saveInRepository(Contribution contribution) {
        _contributionRepository.save(contribution);
    }
    @Override
    public List<ContestDto> getContests(Optional<Predicate<Contest>> predicate) {
        List<Contest> result = predicate.map(contestPredicate -> _contestRepository.findAll()
                        .stream()
                        .filter(contestPredicate)
                        .collect(Collectors.toList()))
                .orElseGet(_contestRepository::findAll);
        if (!result.isEmpty()) {
            return ContestMapper.toDto(result, true);
        }
        return new ArrayList<>();
    }
    @Override
    public ContestDto getContestById(UUID id) {
        Contest contest = _contestRepository.findById(id).orElse(null);
        if (contest != null) {
            return ContestMapper.toDto(contest, true);
        }
        return null;
    }
    @Override
    public ContestDto addContest(ContestDto contestDto) {
        if (!_contestRepository.existsById(contestDto.getId())) {
            Contest contest = ContestMapper.toEntity(contestDto, true);
            _contestRepository.save(contest);
            return contestDto;
        }
        return null;
    }
    @Override
    public ContestDto deleteContestById(UUID id) {
        Contest contest = _contestRepository.findById(id).orElse(null);
        if (contest != null) {
            contest.detachFromEntities();
            _contestRepository.delete(contest);
            return ContestMapper.toDto(contest, true);
        }
        return null;
    }
    @Override
    public List<ContributionDto> getContributions(Optional<Predicate<Contribution>> predicate) {
        List<Contribution> result = predicate.map(contributionPredicate -> _contributionRepository.findAll()
                        .stream()
                        .filter(contributionPredicate)
                        .collect(Collectors.toList()))
                .orElseGet(_contributionRepository::findAll);
        if (!result.isEmpty()) {
            return ContributionMapper.toDto(result, true);
        }
        return new ArrayList<>();
    }
    @Override
    public ContributionDto getContributionById(UUID id) {
        Contribution contribution = _contributionRepository.findById(id).orElse(null);
        if (contribution != null) {
            return ContributionMapper.toDto(contribution, true);
        }
        return null;
    }
    @Override
    public ContributionDto addContribution(UUID idContest, UUID idParticipant, ContributionDto contributionDto) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            var participants = contest.getParticipatingUsers();
            if (participants.stream().anyMatch(AuthenticatedUserCriteria.hasId(idParticipant))) {
                Contribution contribution = ContributionMapper.toEntity(contributionDto, true);
                contribution.setState(ContentState.VALIDABLE);
                contribution.setContest(contest);
                contest.getContributions().add(contribution);
                if (contribution.getContent() instanceof Itinerary) {
                    contributionDto.getItinerary().getItineraryPois().forEach(poiDto -> {
                        contribution.getItinerary().getItineraryPois().add(GenericPOIMapper.toEntity(poiDto, true));
                    });
                }
                _contributionRepository.save(contribution);
                _contestRepository.save(contest);
                return ContributionMapper.toDto(contribution, true);
            }
        }
        return null;
    }
    @Override
    public ContributionDto deleteContributionById(UUID id) {
        Contribution contribution = _contributionRepository.findById(id).orElse(null);
        if (contribution != null) {
            contribution.detachFromEntities(false);
            _contributionRepository.delete(contribution);
            return ContributionMapper.toDto(contribution, true);
        }
        return null;
    }
    @Override
    public ContributionDto validateContribution(UUID idContribution, boolean validate) {
        Contribution contribution = _contributionRepository.findById(idContribution).orElse(null);
        if (contribution != null && contribution.getState().equals(ContentState.VALIDABLE)) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            contribution.setState(newState);
            contribution.getContent().setState(newState);
            _contributionRepository.save(contribution);
            return ContributionMapper.toDto(contribution, true);
        }
        return null;
    }
    @Override
    public ContributionDto getWinnerContribution(UUID idContest) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            List<Contribution> contributions = contest.getContributions()
                    .stream()
                    .filter(ContributionCriteria.isWinner())
                    .toList();
            if (!contributions.isEmpty()) {
                Contribution result = contributions.get(0);
                return ContributionMapper.toDto(result, true);
            }
        }
        return null;
    }
    @Override
    public ContributionDto setWinnerContribution(UUID idContribution) {
        Contribution contribution = _contributionRepository.findById(idContribution).orElse(null);
        if (contribution != null
                && contribution.getState().equals(ContentState.UPLOADABLE)
                && contribution.getResult().equals(ContestResult.LOSER)) {
            contribution.setResult(ContestResult.WINNER);
            contribution.getContest().setHasWinner(true);
            _contributionRepository.save(contribution);
            return ContributionMapper.toDto(contribution, true);
        }
        return null;
    }
    @Override
    public ContributionDto uploadContribution(UUID idContribution) {
        Contribution contribution = _contributionRepository.findById(idContribution).orElse(null);
        if (contribution != null && contribution.getResult().equals(ContestResult.WINNER)) {
            if (contribution.getContent() instanceof Itinerary itinerary) {
                itinerary.setState(ContentState.UPLOADED);
                _itineraryRepository.save(itinerary);
            } else if (contribution.getContent() instanceof POI poi) {
                poi.setState(ContentState.UPLOADED);
                _poiRepository.save(poi);
            }
            ContributionDto result = ContributionMapper.toDto(contribution, true);
            contribution.detachFromEntities(true);
            _contributionRepository.delete(contribution);
            return result;
        }
        return null;
    }
    @Override
    public boolean deleteAllLoserContributions(Optional<UUID> idContest) {
        Predicate<Contest> predicate = contest -> true;
        if (idContest.isPresent()) {
            predicate = contest -> contest.getId().equals(idContest.get());
        }
        List<Contest> result = _contestRepository.findAll()
                .stream()
                .filter(predicate)
                .toList();
        if (!result.isEmpty()) {
            result.forEach(contest -> contest.getContributions().forEach(contribution -> {
                if (contribution.getResult().equals(ContestResult.LOSER)) {
                    contribution.setState(ContentState.REMOVABLE);
                    _contributionRepository.save(contribution);
                }
            }));
            _contestRepository.saveAll(result);
            return true;
        }
        return false;
    }
    @Override
    public List<AuthenticatedUserDto> getParticipants(UUID idContest, Optional<Predicate<AuthenticatedUser>> predicate) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            List<AuthenticatedUser> result = predicate.map(authenticatedTouristPredicate ->
                    contest.getParticipatingUsers().stream().filter(authenticatedTouristPredicate).toList()
            ).orElseGet(contest::getParticipatingUsers);
            if (!result.isEmpty()) {
                return GenericAuthenticatedUserMapper.toDto(result, true);
            }
        }
        return new ArrayList<>();
    }
    @Override
    public AuthenticatedUserDto getParticipantById(UUID idContest, UUID idParticipant) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            AuthenticatedUser authenticatedUser = contest.getParticipatingUsers()
                    .stream()
                    .filter(AuthenticatedUserCriteria.hasId(idParticipant))
                    .findFirst()
                    .orElse(null);
            return GenericAuthenticatedUserMapper.toDto(authenticatedUser, true);
        }
        return null;
    }
    @Override
    public AuthenticatedUserDto addParticipant(UUID idContest, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null && !contest.getParticipatingUsers().contains(authenticatedUser)) {
            contest.getParticipatingUsers().add(authenticatedUser);
            authenticatedUser.getParticipatedContests().add(contest);
            _contestRepository.save(contest);
            return GenericAuthenticatedUserMapper.toDto(authenticatedUser, true);
        }
        return null;
    }
    @Override
    public AuthenticatedUserDto deleteParticipant(UUID idContest, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null && contest.getParticipatingUsers().contains(authenticatedUser)) {
            contest.getParticipatingUsers().remove(authenticatedUser);
            authenticatedUser.getParticipatedContests().remove(contest);
            _contestRepository.save(contest);
            return GenericAuthenticatedUserMapper.toDto(authenticatedUser, true);
        }
        return null;
    }
}
