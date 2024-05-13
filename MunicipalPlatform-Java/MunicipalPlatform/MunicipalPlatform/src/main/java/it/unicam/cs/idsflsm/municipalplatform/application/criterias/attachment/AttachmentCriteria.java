package it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;
import java.util.function.Predicate;
public class AttachmentCriteria {
    public static Predicate<Attachment> isPendingAttachment() {
        return attachment -> attachment.getClass() == PendingAttachment.class;
    }
    public static Predicate<Attachment> isAuthorizedAttachment() {
        return attachment -> attachment.getClass() == AuthorizedAttachment.class;
    }
    public static Predicate<Attachment> isInRemovableState() {
        return attachment -> attachment.getState().equals(ContentState.REMOVABLE);
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
    public static Predicate<Attachment> hasId(UUID id) {
        if (id != null) {
            return attachment -> attachment.getId().equals(id);
        } else {
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasName(String name) {
        if (!name.isBlank()) {
            return attachment -> attachment.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasDescription(String description) {
        if (!description.isBlank()) {
            return attachment -> attachment.getDescription().toLowerCase().contains(description.toLowerCase());
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasAuthor(String author) {
        if (!author.isBlank()) {
            return attachment -> attachment.getAuthor().toLowerCase().contains(author.toLowerCase());
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasCreationDate(Date creationDate) {
        if (creationDate != null) {
            return attachment -> attachment.getCreationDate().equals(creationDate);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasExpiryDate(Date expiryDate) {
        if (expiryDate != null) {
            return attachment -> attachment.getExpiryDate().equals(expiryDate);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Attachment> hasState(ContentState state) {
        if (state != null) {
            return attachment -> attachment.getState().equals(state);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }

    // TODO : MAYBE BUILDER DESIGN PATTERN

    @SafeVarargs
    public static Predicate<Attachment> criteriaBuilder(Predicate<Attachment>... predicates) {
        Predicate<Attachment> result = attachment -> true;
        for (Predicate<Attachment> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
