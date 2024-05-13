package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReportRequest extends ModifyReportRequest {
    public UpdateReportRequest() {
    }
    public UpdateReportRequest(String motivation) {
        super(motivation);
    }
}
