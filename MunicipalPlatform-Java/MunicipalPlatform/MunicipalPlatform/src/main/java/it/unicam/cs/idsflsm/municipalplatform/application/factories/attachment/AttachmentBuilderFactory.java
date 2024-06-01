package it.unicam.cs.idsflsm.municipalplatform.application.factories.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.AuthorizedAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.IAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment.PendingAttachmentBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@AllArgsConstructor
public class AttachmentBuilderFactory {
    private final PendingAttachmentBuilder _pendingAttachmentBuilder;
    private final AuthorizedAttachmentBuilder _authorizedAttachmentBuilder;
    public IAttachmentBuilder createAttachmentBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.AUTHTOURIST_ATTACHMENT_CREATE)) {
            return _pendingAttachmentBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED)) {
            return _authorizedAttachmentBuilder;
        }
        return null;
    }
}
