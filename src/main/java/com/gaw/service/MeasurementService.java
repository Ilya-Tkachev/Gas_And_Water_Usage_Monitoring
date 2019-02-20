package com.gaw.service;

import com.gaw.model.entity.Measurement;
import com.gaw.model.entity.MeasurementType;
import com.gaw.model.entity.User;
import com.gaw.model.view.MeasurementView;
import com.gaw.repository.MeasurementRepository;
import com.gaw.repository.MeasurementTypeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeasurementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
    private final MeasurementRepository measurementRepository;
    private final MeasurementTypeRepository measurementTypeRepository;
    private final UserService userService;

    public Map<String, List<MeasurementView>> getByUserName(String userName) {
        if (Objects.nonNull(userName) && userService.userExists(userName)) {
            List<MeasurementType> measurementTypes = measurementTypeRepository.findAll();
            List<MeasurementView> measurementViews = measurementRepository.findByUserName(userName)
                    .stream()
                    .map(e -> parse(e, measurementTypes))
                    .collect(Collectors.toList());
            LOGGER.debug(String.format("User %s queried %d entities.", userName, measurementViews.size()));
            return measurementViews.stream().collect(Collectors.groupingBy(MeasurementView::getMeasurementName));
        } else {
            throw new RuntimeException("User with email %s not found.");
        }
    }

    private MeasurementView parse(Measurement measurement, List<MeasurementType> measurementTypes) {
        String typeName = measurementTypes.stream()
                .filter(e -> e.getId().equals(measurement.getMeasurementId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such Id"))
                .getName();
        return MeasurementView.builder()
                .measurementName(typeName)
                .amount(measurement.getAmount())
                .created(measurement.getCreated().toString())
                .build();
    }

    public Map<Long, List<Measurement>> submit(List<MeasurementView> measurementViews, String email) {
        if (containsUnique(measurementViews)) {
            User user = userService.getUserByName(email);
            List<MeasurementType> measurementTypes = measurementTypeRepository.findAll();
            List<Measurement> measurements = measurementViews
                    .stream()
                    .map(e -> parse(e, user, measurementTypes))
                    .collect(Collectors.toList());
            return measurementRepository.saveAll(measurements)
                    .stream()
                    .collect(Collectors.groupingBy(Measurement::getMeasurementId));
        } else {
            throw new RuntimeException("Measurement type duplication is not allowed.");
        }
    }

    private boolean containsUnique(List<MeasurementView> measurementViews) {
        Set<String> set = new HashSet<>();
        for (MeasurementView measurementView : measurementViews) {
            if (!set.add(measurementView.getMeasurementName())) return false;
        }
        return true;
    }

    private Measurement parse(MeasurementView measurementView, User user, List<MeasurementType> measurementTypes) {
        String typeName = measurementView.getMeasurementName();
        Long typeId = measurementTypes.stream()
                .filter(e -> e.getName().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Type with name %s not found.", typeName)))
                .getId();
        return Measurement.builder()
                .amount(measurementView.getAmount())
                .created(new Date())
                .user(user)
                .measurementId(typeId)
                .build();
    }

}