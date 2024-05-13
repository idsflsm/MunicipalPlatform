package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddReportRequest extends ModifyReportRequest {
    public AddReportRequest() {
    }
    public AddReportRequest(String motivation) {
        super(motivation);
    }
}
