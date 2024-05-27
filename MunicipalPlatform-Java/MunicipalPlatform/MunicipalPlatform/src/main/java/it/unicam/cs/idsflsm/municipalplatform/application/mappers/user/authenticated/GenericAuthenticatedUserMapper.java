package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.*;

import java.util.List;
import java.util.stream.Collectors;
public class GenericAuthenticatedUserMapper {
    public static AuthenticatedUserDto toDto(AuthenticatedUser authenticatedUser, boolean includeRelativeEntities) {
        if (authenticatedUser != null) {
            if (authenticatedUser instanceof AuthenticatedTourist authenticatedTourist) {
                return AuthenticatedTouristMapper.toDto(authenticatedTourist, includeRelativeEntities);
            } else if (authenticatedUser instanceof Contributor contributor) {
                return ContributorMapper.toDto(contributor, includeRelativeEntities);
            } else if (authenticatedUser instanceof AuthorizedContributor authorizedContributor) {
                return AuthorizedContributorMapper.toDto(authorizedContributor, includeRelativeEntities);
            } else if (authenticatedUser instanceof Curator curator) {
                return CuratorMapper.toDto(curator, includeRelativeEntities);
            } else if (authenticatedUser instanceof Animator animator) {
                return AnimatorMapper.toDto(animator, includeRelativeEntities);
            } else if (authenticatedUser instanceof Administrator administrator) {
                return AdministratorMapper.toDto(administrator, includeRelativeEntities);
            }
        }
        return null;
    }
    public static AuthenticatedUser toEntity(AuthenticatedUserDto authenticatedUserDto, boolean includeRelativeEntities) {
        if (authenticatedUserDto != null) {
            if (authenticatedUserDto instanceof AuthenticatedTouristDto authenticatedTouristDto) {
                return AuthenticatedTouristMapper.toEntity(authenticatedTouristDto, includeRelativeEntities);
            } else if (authenticatedUserDto instanceof ContributorDto contributorDto) {
                return ContributorMapper.toEntity(contributorDto, includeRelativeEntities);
            } else if (authenticatedUserDto instanceof AuthorizedContributorDto authorizedContributorDto) {
                return AuthorizedContributorMapper.toEntity(authorizedContributorDto, includeRelativeEntities);
            } else if (authenticatedUserDto instanceof CuratorDto curatorDto) {
                return CuratorMapper.toEntity(curatorDto, includeRelativeEntities);
            } else if (authenticatedUserDto instanceof AnimatorDto animatorDto) {
                return AnimatorMapper.toEntity(animatorDto, includeRelativeEntities);
            } else if (authenticatedUserDto instanceof AdministratorDto administratorDto) {
                return AdministratorMapper.toEntity(administratorDto, includeRelativeEntities);
            }
        }
        return null;
    }
    public static List<AuthenticatedUserDto> toDto(List<AuthenticatedUser> authenticatedUsers, boolean includeRelativeEntities) {
        if (authenticatedUsers != null) {
            return authenticatedUsers.stream()
                    .map(authenticatedUser -> toDto(authenticatedUser, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthenticatedUser> toEntity(List<AuthenticatedUserDto> authenticatedUserDtos, boolean includeRelativeEntities) {
        if (authenticatedUserDtos != null) {
            return authenticatedUserDtos.stream()
                    .map(authenticatedUserDto -> toEntity(authenticatedUserDto, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
