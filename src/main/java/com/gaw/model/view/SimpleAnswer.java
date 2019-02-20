package com.gaw.model.view;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class SimpleAnswer {
    private String message;
    private Map<String, List<MeasurementView>> measurementView;
}