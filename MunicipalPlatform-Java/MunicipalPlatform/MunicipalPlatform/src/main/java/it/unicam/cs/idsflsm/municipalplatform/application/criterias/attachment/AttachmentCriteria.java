package it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;

import java.util.function.Predicate;
public class AttachmentCriteria {
    public static Predicate<Attachment> isPendingAttachment() {
        return attachment -> attachment.getClass() == PendingAttachment.class;
    }

    public static Predicate<Attachment> isAuthorizedAttachment() {
        return attachment -> attachment.getClass() == AuthorizedAttachment.class;
    }

    public static Predicate<Attachment> isInDisposableState() {
        return attachment -> attachment.getState().equals(ContentState.DISPOSABLE);
    }

    public static Predicate<Attachment> isInValidableState() {
        return attachment -> attachment.getState().equals(ContentState.VALIDABLE);
    }

    public static Predicate<Attachment> isInUploadableState() {
        return attachment -> attachment.getState().equals(ContentState.UPLOADABLE);
    }

    public static Predicate<Attachment> isInUploadedState() {
        return attachment -> attachment.getState().equals(ContentState.UPLOADED);
    }

}
