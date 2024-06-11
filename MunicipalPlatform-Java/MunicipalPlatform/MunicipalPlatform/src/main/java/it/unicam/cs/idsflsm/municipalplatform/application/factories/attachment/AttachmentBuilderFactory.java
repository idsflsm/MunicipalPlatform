package it.unicam.cs.idsflsm.municipalplatform.application.factories.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.AuthorizedAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.IAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.PendingAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Builder factory class for the creation of different types of Attachment builders
 */
@Component
@AllArgsConstructor
public class AttachmentBuilderFactory {
    /**
     * The builder for creating PendingAttachment instances
     */
    private final PendingAttachmentBuilder _pendingAttachmentBuilder;
    /**
     * The builder for creating AuthorizedAttachment instances
     */
    private final AuthorizedAttachmentBuilder _authorizedAttachmentBuilder;
    /**
     * Method for creating an Attachment builder based on the provided user permission
     *
     * @param permission the user permission determining the type of builder to be created
     * @return an Attachment builder corresponding to the user permission,
     * if a matching builder is found, null otherwise
     */
    public IAttachmentBuilder createAttachmentBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.AUTHTOURIST_ATTACHMENT_CREATE)) {
            return _pendingAttachmentBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED)
                || permission.equals(UserPermission.CURATOR_ATTACHMENT_UPDATE)) {
            return _authorizedAttachmentBuilder;
        }
        return null;
    }
}
