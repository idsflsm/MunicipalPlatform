package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the possible permissions that a user can have
 */
@RequiredArgsConstructor
public enum UserPermission {
    /**
     * Enables the visualization of contents uploaded to the platform
     */
    TOURIST_CONTENT_READ("tourist:content:read"),
    /**
     * Enables the creation of a report related to a specific attachment
     */
    TOURIST_REPORT_CREATE("tourist:report:create"),

    /**
     * Enables the creation of an attachment, waiting to be validated
     */
    AUTHTOURIST_ATTACHMENT_CREATE("authtourist:attachment:create"),
    /**
     * Enables the saving of a content uploaded to the platform
     */
    AUTHTOURIST_CONTENT_SAVE("authtourist:content:save"),
    /**
     * Enables participation in a contest
     */
    AUTHTOURIST_CONTEST_PARTICIPATE("authtourist:contest:participate"),
    /**
     * Enables the creation of a contest contribution
     */
    AUTHTOURIST_CONTRIBUTION_CREATE("authtourist:contribution:create"),
    /**
     * Enables the sending of a request for role change
     */
    AUTHTOURIST_USER_ROLE_SEND("authtourist:user:role:send"),

    /**
     * Enables the creation of a content, waiting to be validated
     */
    CONTRIBUTOR_CONTENT_CREATE_PENDING("contributor:content:create:pending"),

    /**
     * Enables the creation of a content, waiting to be uploaded
     */
    AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED("authcontributor:content:create:authorized"),
    /**
     * Enables the creation of an attachment, waiting to be uploaded
     */
    AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED("authcontributor:attachment:create:authorized"),
    /**
     * Enables the upload of a content to the platform
     */
    AUTHCONTRIBUTOR_CONTENT_UPLOAD("authcontributor:content:upload"),
    /**
     * Enables the upload of an attachment to the platform
     */
    AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD("authcontributor:attachment:upload"),

    /**
     * Enables the creation of a contest
     */
    ANIMATOR_CONTEST_CREATE("animator:contest:create"),
    /**
     * Enables the validation of a contest
     */
    ANIMATOR_CONTEST_VALIDATE("animator:contest:validate"),
    /**
     * Enables the visualization of contest contributions
     */
    ANIMATOR_CONTRIBUTION_READ("animator:contribution:read"),
    /**
     * Enables the upload of a contest contribution
     */
    ANIMATOR_CONTRIBUTION_UPLOAD("animator:contribution:upload"),
    /**
     * Enables marking a contest contribution as a winner
     */
    ANIMATOR_CONTRIBUTION_WINNER_SET("animator:contribution:winner:set"),
    /**
     * Enables the elimination of losing contribution
     */
    ANIMATOR_CONTRIBUTION_LOSER_DELETE("animator:contribution:loser:delete"),
    /**
     * Enables the visualization of contest participants
     */
    ANIMATOR_CONTEST_PARTICIPANT_READ("animator:contest:participant:read"),
    /**
     * Enable the elimination of a user as a contest participant
     */
    ANIMATOR_CONTEST_PARTICIPANT_DELETE("animator:contest:participant:delete"),

    /**
     * Enables the validation of a content
     */
    CURATOR_CONTENT_VALIDATE("curator:content:validate"),
    /**
     * Enables the validation of an attachment
     */
    CURATOR_ATTACHMENT_VALIDATE("curator:attachment:validate"),
    /**
     * Enables the visualization of reports
     */
    CURATOR_REPORT_READ("curator:report:read"),
    /**
     * Enables the elimination of a report
     */
    CURATOR_REPORT_DELETE("curator:report:delete"),
    /**
     * Enables the validation of a report
     */
    CURATOR_REPORT_VALIDATE("curator:report:validate"),
    /**
     * Enables the update of a content already present on the platform
     */
    CURATOR_CONTENT_UPDATE("curator:content:update"),
    /**
     * Enables the update of an attachment already present on the platform
     */
    CURATOR_ATTACHMENT_UPDATE("curator:attachment:update"),

    /**
     * Enables the visualization of users' requested roles
     */
    ADMINISTRATOR_USER_ROLE_RECEIVE("administrator:user:role:receive"),
    /**
     * Enables the update of the role of a user
     */
    ADMINISTRATOR_USER_ROLE_UPDATE("administrator:user:role:update"),
    /**
     * Enables the elimination of user's role
     */
    ADMINISTRATOR_USER_ROLE_DELETE("administrator:user:role:delete");

    @Getter
    private final String permission;
}
