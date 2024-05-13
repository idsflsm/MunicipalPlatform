package it.unicam.cs.idsflsm.municipalplatform.application.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest.IContestService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContributionCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContributionMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContributionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ContestService implements IContestService {
    private final IContestRepository _contestRepository;
    private final IContributionRepository _contributionRepository;
    @Override
    public void saveInRepository(Contest contest) {
        _contestRepository.save(contest);
    }
    @Override
    public void saveInRepository(Contribution contribution) {
        _contributionRepository.save(contribution);
    }
    @Override
    public List<ContestDto> getAllContests(Optional<Predicate<Contest>> predicate) {
        List<Contest> result = predicate.map(contestPredicate -> _contestRepository.findAll()
                    .stream()
                    .filter(contestPredicate)
                    .collect(Collectors.toList()))
                .orElseGet(_contestRepository::findAll);
        if (!result.isEmpty()) {
            return ContestMapper.toDto(result);
        } else {
            return null;
        }
    }
    @Override
    public ContestDto getContestById(UUID id) {
        Optional<Contest> contest = _contestRepository.findById(id);
        return contest.map(ContestMapper::toDto)
                .orElse(null);
    }
    @Override
    public boolean addContest(ContestDto contestDto) {
        if (!_contestRepository.existsById(contestDto.getId())) {
            Contest contest = ContestMapper.toEntity(contestDto);
            _contestRepository.save(contest);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteContestById(UUID id) {
        if (getContestById(id) != null) {
            _contestRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
//    @Override
//    public boolean deleteContest(ContestDto contestDto, Optional<Predicate<Contest>> predicate) {
//        if (getAllContests(predicate).get(0) != null) {
//            Contest contest = ContestMapper.toEntity(contestDto);
//            assert contest != null;
//            _contestRepository.delete(contest);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public List<ContributionDto> getAllContributions(Optional<Predicate<Contribution>> predicate) {
        List<Contribution> result = predicate.map(contributionPredicate -> _contributionRepository.findAll()
                    .stream()
                    .filter(contributionPredicate)
                    .collect(Collectors.toList()))
                .orElseGet(_contributionRepository::findAll);
        if (!result.isEmpty()) {
            return ContributionMapper.toDto(result);
        } else {
            return null;
        }
    }
    @Override
    public ContributionDto getContributionById(UUID id) {
        Optional<Contribution> contribution = _contributionRepository.findById(id);
        return contribution.map(ContributionMapper::toDto)
                .orElse(null);
    }
    @Override
    public boolean addContribution(UUID idContest, ContributionDto contributionDto) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            Contribution contribution = ContributionMapper.toEntity(contributionDto);
            contribution.setState(ContentState.VALIDABLE);
            contribution.setContest(contest);
            contest.getContributions().add(contribution);
            _contestRepository.save(contest);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteContributionById(UUID id) {
        Contribution contribution = _contributionRepository.findById(id).orElse(null);
        if (contribution != null) {
            contribution.setState(ContentState.REMOVABLE);
            _contributionRepository.save(contribution);
            return true;
        } else {
            return false;
        }
    }
//    @Override
//    public boolean deleteContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate) {
//        if (getAllContributions(predicate).get(0) != null) {
//            Contribution contribution = ContributionMapper.toEntity(contributionDto);
//            assert contribution != null;
//            contribution.setState(ContentState.Removable);
//            // _contributionRepository.delete(contribution);
//            _contributionRepository.save(contribution);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public boolean validateContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate, boolean validate) {
        Contribution contribution = ContributionMapper.toEntity(contributionDto);
        assert contribution != null;
        if (getAllContributions(predicate).get(0) != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            contribution.setState(newState);
            _contributionRepository.save(contribution);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public ContributionDto getWinnerContribution(UUID idContest) {
        Optional<Contest> contest = _contestRepository.findById(idContest);
        if (contest.isPresent()) {
            Contribution result = contest.get().getContributions()
                    .stream()
                    .filter(ContributionCriteria.isWinner())
                    .toList()
                    .get(0);
            return ContributionMapper.toDto(result);
        } else {
            return null;
        }
    }
    @Override
    public Optional<Contribution> uploadContribution(ContributionDto contributionDto) {
        Contribution contribution = ContributionMapper.toEntity(contributionDto);
        if (contribution != null) {
            // TODO : EXTRACTION OF POI / ITINERARY IN CONTRIBUTION, IN ORDER TO SAVE IT IN POI / ITINERARY REPO
            _contributionRepository.delete(contribution);
//            contribution.setState(ContentState.UPLOADED);
//            _contributionRepository.save(contribution);
            return Optional.of(contribution);
        } else {
            return Optional.empty();
        }
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
                contribution.setState(ContentState.REMOVABLE);
                _contributionRepository.save(contribution);
            }));
            _contestRepository.saveAll(result);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<AuthenticatedTouristDto> getAllParticipants(UUID idContest, Optional<Predicate<AuthenticatedTourist>> predicate) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            List<AuthenticatedTourist> result = predicate.map(authenticatedTouristPredicate ->
                contest.getParticipatingTourists().stream().filter(authenticatedTouristPredicate).toList()
            ).orElseGet(contest::getParticipatingTourists);
            if (!result.isEmpty()) {
                return AuthenticatedTouristMapper.toDto(result);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    @Override
    public AuthenticatedTouristDto getParticipantById(UUID idContest, UUID idAuthenticatedTourist) {
        Contest contest = _contestRepository.findById(idContest).orElse(null);
        if (contest != null) {
            AuthenticatedTourist authenticatedTourist = contest.getParticipatingTourists()
                    .stream()
                    .filter(p -> p.getId().equals(idAuthenticatedTourist))
                    .findFirst()
                    .orElse(null);
            return AuthenticatedTouristMapper.toDto(authenticatedTourist);
        } else {
            return null;
        }
    }
    @Override
    public boolean addParticipant(UUID idContest, AuthenticatedTouristDto touristDto) {
        AuthenticatedTourist authenticatedTourist = AuthenticatedTouristMapper.toEntity(touristDto);
        assert authenticatedTourist != null;
        Optional<Contest> contest = _contestRepository.findById(idContest);
        if (contest.isPresent()) {
            contest.get().getParticipatingTourists().add(authenticatedTourist);
            _contestRepository.save(contest.get());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteParticipant(UUID idContest, AuthenticatedTouristDto touristDto) {
        AuthenticatedTourist authenticatedTourist = AuthenticatedTouristMapper.toEntity(touristDto);
        assert authenticatedTourist != null;
        Optional<Contest> contest = _contestRepository.findById(idContest);
        if (contest.isPresent()) {
            contest.get().getParticipatingTourists().remove(authenticatedTourist);
            _contestRepository.save(contest.get());
            return true;
        } else {
            return false;
        }
    }
}
