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
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContributionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ContestService implements IContestService {
    private final IContestRepository _contestRepository;
    private final IContributionRepository _contributionRepository;
    @Override
    public List<ContestDto> getAllContests(Optional<Predicate<Contest>> predicate) {
        List<Contest> result = predicate.map(contestPredicate -> _contestRepository.findAll()
                    .stream()
                    .filter(contestPredicate)
                    .collect(Collectors.toList()))
                .orElseGet(_contestRepository::findAll);
        return ContestMapper.toDto(result);
    }
    @Override
    public ContestDto getContestById(UUID id) {
        Optional<Contest> contest = _contestRepository.findById(id);
        return contest.map(ContestMapper::toDto)
                .orElse(null);
    }
    @Override
    public List<ContributionDto> getAllContributions(Optional<Predicate<Contribution>> predicate) {
        List<Contribution> result = predicate.map(contributionPredicate -> _contributionRepository.findAll()
                    .stream()
                    .filter(contributionPredicate)
                    .collect(Collectors.toList()))
                .orElseGet(_contributionRepository::findAll);
        return ContributionMapper.toDto(result);
    }
    @Override
    public ContributionDto getContributionById(UUID id) {
        Optional<Contribution> contribution = _contributionRepository.findById(id);
        return contribution.map(ContributionMapper::toDto)
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
    public boolean addContribution(ContributionDto contributionDto) {
        Contest contest = contributionDto.getContest();
        if (_contestRepository.existsById(contest.getId())) {
            Contribution contribution = ContributionMapper.toEntity(contributionDto);
            contest.getContributions().add(contribution);
            _contributionRepository.save(contribution);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addParticipant(UUID idContest, AuthenticatedTouristDto touristDto) {
        AuthenticatedTourist authenticatedTourist = AuthenticatedTouristMapper.toEntity(touristDto);
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
    public boolean deleteContestById(UUID id) {
        if (getContestById(id) != null) {
            _contestRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteContest(ContestDto contestDto, Optional<Predicate<Contest>> predicate) {
        if (getAllContests(predicate).get(0) != null) {
            Contest contest = ContestMapper.toEntity(contestDto);
            assert contest != null;
            _contestRepository.delete(contest);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteContributionById(UUID id) {
        if (getContributionById(id) != null) {
            _contributionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate) {
        if (getAllContributions(predicate).get(0) != null) {
            Contribution contribution = ContributionMapper.toEntity(contributionDto);
            assert contribution != null;
            _contributionRepository.delete(contribution);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public ContributionDto getWinnerContribution(UUID idContest) {
        Optional<Contribution> contribution = _contributionRepository.findById(idContest);
        if (contribution.isPresent()) {
            Contribution result = _contributionRepository.findAll()
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
    public boolean uploadContribution(ContributionDto contributionDto) {
        if (_contributionRepository.existsById(contributionDto.getId())) {
            Contribution contribution = ContributionMapper.toEntity(contributionDto);
            _contributionRepository.save(contribution);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteAllLoserContributions(Optional<UUID> idContest) {
        Predicate<Contribution> optional = contribution -> true;
        if (idContest.isPresent()) {
            optional = (contribution) -> (contribution.getContest().getId().equals(idContest.get()));
        }
        List<Contribution> result = _contributionRepository.findAll()
                .stream()
                .filter(ContributionCriteria.isLoser().and(optional))
                .toList();
        if (!result.isEmpty()) {
            _contributionRepository.deleteAll(result);
            return true;
        } else {
            return false;
        }
    }
}
