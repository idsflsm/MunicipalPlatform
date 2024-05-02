package it.unicam.cs.idsflsm.municipalplatform.api.controllers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.services.attachment.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/attachments")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentController {
    private final IAttachmentService _attachmentService;
}
