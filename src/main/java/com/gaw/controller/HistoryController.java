package com.gaw.controller;

import com.gaw.model.view.MeasurementView;
import com.gaw.model.view.SimpleAnswer;
import com.gaw.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/histories")
@AllArgsConstructor
public class HistoryController {

    private final MeasurementService measurementService;

    @GetMapping("/{email}")
    public ResponseEntity getHistory(/*Authentication authentication*/@PathVariable("email") String email) { //TODO add user auth later
        try {
            Map<String, List<MeasurementView>> result = measurementService.getByUserName(/*authentication.name()*/email);
            return ResponseEntity.ok().body(SimpleAnswer.builder().measurementView(result).build()); // TODO use user's email from auth
        } catch (Exception e) {
            return badRequest().body(SimpleAnswer.builder().message(e.toString()).build());
        }
    }

}