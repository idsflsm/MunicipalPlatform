package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidUserRoleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission.*;
/**
 * Represents the possible roles (and associated permissions)
 * that a user can have
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
    /**
     * A user that is not registered on the platform
     */
    TOURIST(Set.of(
            TOURIST_CONTENT_READ,
            TOURIST_REPORT_CREATE
    )),
    /**
     * An authenticated user that has basic permissions, such as creation of
     * pending validation attachments and participation to a contest
     */
    AUTHENTICATED_TOURIST(Set.of(
            TOURIST_CONTENT_READ,
            TOURIST_REPORT_CREATE,
            AUTHTOURIST_ATTACHMENT_CREATE,
            AUTHTOURIST_CONTENT_SAVE,
            AUTHTOURIST_CONTEST_PARTICIPATE,
            AUTHTOURIST_CONTRIBUTION_CREATE,
            AUTHTOURIST_USER_ROLE_SEND
    )),
    /**
     * An authenticated user that can contribute to the platform by adding
     * new pending validation contents and attachments, also through
     * participation in a contest
     */
    CONTRIBUTOR(Set.of(
            TOURIST_REPORT_CREATE,
            AUTHTOURIST_ATTACHMENT_CREATE,
            AUTHTOURIST_CONTENT_SAVE,
            AUTHTOURIST_CONTEST_PARTICIPATE,
            AUTHTOURIST_CONTRIBUTION_CREATE,
            CONTRIBUTOR_CONTENT_CREATE_PENDING,
            AUTHTOURIST_USER_ROLE_SEND
    )),
    /**
     * An authenticated user that can contribute to the platform by adding
     * new pending upload contents and attachments, also through
     * participation in a contest, and by uploading validated contents and
     * attachments
     */
    AUTHORIZED_CONTRIBUTOR(Set.of(
            TOURIST_REPORT_CREATE,
            AUTHTOURIST_CONTENT_SAVE,
            AUTHTOURIST_CONTEST_PARTICIPATE,
            AUTHTOURIST_CONTRIBUTION_CREATE,
            AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED,
            AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED,
            AUTHCONTRIBUTOR_CONTENT_UPLOAD,
            AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD,
            AUTHTOURIST_USER_ROLE_SEND
    )),
    /**
     * An authenticated user that can contribute to the platform by creating
     * new contests. He also manages (through validation, assignment as winner, loading)
     * the contributions related to the created contest
     */
    ANIMATOR(Set.of(
            ANIMATOR_CONTEST_CREATE,
            ANIMATOR_CONTEST_VALIDATE,
            ANIMATOR_CONTRIBUTION_READ,
            ANIMATOR_CONTRIBUTION_UPLOAD,
            ANIMATOR_CONTRIBUTION_WINNER_SET,
            ANIMATOR_CONTRIBUTION_LOSER_DELETE,
            ANIMATOR_CONTEST_PARTICIPANT_READ,
            ANIMATOR_CONTEST_PARTICIPANT_DELETE,
            AUTHTOURIST_USER_ROLE_SEND
    )),
    /**
     * An authenticated user that can contribute to the platform by
     * adding and updating pending upload contents and attachments,
     * also through participation in a contest, and by uploading validated
     * contents and attachments. He also validates contents and attachments
     * created by others, and analyses reports
     */
    CURATOR(Set.of(
            TOURIST_REPORT_CREATE,
            AUTHTOURIST_CONTENT_SAVE,
            AUTHTOURIST_CONTEST_PARTICIPATE,
            AUTHTOURIST_CONTRIBUTION_CREATE,
            AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED,
            AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED,
            AUTHCONTRIBUTOR_CONTENT_UPLOAD,
            AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD,
            CURATOR_CONTENT_VALIDATE,
            CURATOR_ATTACHMENT_VALIDATE,
            CURATOR_REPORT_READ,
            CURATOR_REPORT_DELETE,
            CURATOR_REPORT_VALIDATE,
            CURATOR_CONTENT_UPDATE,
            CURATOR_ATTACHMENT_UPDATE,
            AUTHTOURIST_USER_ROLE_SEND
    )),
    /**
     * An authenticated user that manages the assignment of roles requested by users,
     * and the elimination of roles belonging to users themselves
     */
    ADMINISTRATOR(Set.of(
            ADMINISTRATOR_USER_ROLE_RECEIVE,
            ADMINISTRATOR_USER_ROLE_UPDATE,
            ADMINISTRATOR_USER_ROLE_DELETE
    ));
    private final Set<UserPermission> permissions;
    /**
     * Method for converting a string to a UserRole value
     *
     * @param text string value to be converted
     * @return the value of UserRole associated with the string
     * if the latter is not null, null otherwise
     * @throws InvalidContentStateException if string is not null
     *                                      but cannot be converted to any UserRole value
     */
    public static UserRole fromString(String text) throws InvalidContentStateException {
        if (text != null) {
            for (UserRole role : UserRole.values()) {
                if (role.name().equalsIgnoreCase(text)) {
                    return role;
                }
            }
            throw new InvalidUserRoleException(text);
        }
        return null;
    }
}
