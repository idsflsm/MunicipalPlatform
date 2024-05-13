package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateAttachmentRequest extends ModifyAttachmentRequest {
    public UpdateAttachmentRequest() {
    }
    public UpdateAttachmentRequest(String name, String description, String author, String expiryDate) {
        super(name, description, author, expiryDate);
    }
}
