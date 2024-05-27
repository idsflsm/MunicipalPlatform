package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public enum UserPermission {
    TOURIST_CONTENT_READ("tourist:content:read"),
    TOURIST_REPORT_CREATE("tourist:report:create"),

    AUTHTOURIST_ATTACHMENT_CREATE("authtourist:attachment:create"),
    AUTHTOURIST_CONTENT_SAVE("authtourist:content:save"),
    AUTHTOURIST_CONTEST_PARTICIPATE("authtourist:contest:participate"),
    AUTHTOURIST_CONTRIBUTION_CREATE("authtourist:contribution:create"),
    AUTHTOURIST_USER_ROLE_SEND("authtourist:user:role:send"),

    CONTRIBUTOR_CONTENT_CREATE_PENDING("contributor:content:create:pending"),
    // CONTRIBUTOR_ATTACHMENT_CREATE("contributor:attachment:create"),

    AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED("authcontributor:content:create:authorized"),
    AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED("authcontributor:attachment:create:authorized"),
    AUTHCONTRIBUTOR_CONTENT_UPLOAD("authcontributor:content:upload"),
    AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD("authcontributor:attachment:upload"),

    ANIMATOR_CONTEST_CREATE("animator:contest:create"),
    ANIMATOR_CONTEST_VALIDATE("animator:contest:validate"),
    ANIMATOR_CONTRIBUTION_READ("animator:contribution:read"),
    ANIMATOR_CONTRIBUTION_UPLOAD("animator:contribution:upload"),
    ANIMATOR_CONTRIBUTION_WINNER_SET("animator:contribution:winner:set"),
    ANIMATOR_CONTRIBUTION_LOSER_DELETE("animator:contribution:loser:delete"),
    ANIMATOR_CONTEST_PARTICIPANT_READ("animator:contest:participant:read"),
    ANIMATOR_CONTEST_PARTICIPANT_DELETE("animator:contest:participant:delete"),

    CURATOR_CONTENT_VALIDATE("curator:content:validate"),
    CURATOR_ATTACHMENT_VALIDATE("curator:attachment:validate"),
    CURATOR_REPORT_READ("curator:report:read"),
    CURATOR_REPORT_DELETE("curator:report:delete"),
    CURATOR_REPORT_VALIDATE("curator:report:validate"),
    CURATOR_CONTENT_UPDATE("curator:content:update"),
    CURATOR_ATTACHMENT_UPDATE("curator:attachment:update"),

    ADMINISTRATOR_USER_ROLE_RECEIVE("administrator:user:role:receive"),
    ADMINISTRATOR_USER_ROLE_UPDATE("administrator:user:role:update"),
    ADMINISTRATOR_USER_ROLE_DELETE("administrator:user:role:delete");

    @Getter
    private final String permission;
}
