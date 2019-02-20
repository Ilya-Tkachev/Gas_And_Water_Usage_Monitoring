package com.gaw.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementView {
    private Double amount;
    private String created;
    private String measurementName;
}
