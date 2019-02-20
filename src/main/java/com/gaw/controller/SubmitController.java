package com.gaw.controller;

import com.gaw.model.view.MeasurementView;
import com.gaw.model.view.SimpleAnswer;
import com.gaw.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/submits")
@AllArgsConstructor
public class SubmitController {

    private final MeasurementService measurementService;

    @PostMapping("/{email}") //use list instead of Set to inform user about dublicates instead of just swallowing them
    public ResponseEntity synchronize(/*Authentication authentication*/@PathVariable("email") String email, @RequestBody List<MeasurementView> appointmentViews) { //TODO add user auth later
        try { //TODO discuss validation of measurement data later
            return ResponseEntity.ok(measurementService.submit(appointmentViews, /*authentication.name()*/email)); // TODO use user's email from auth
        } catch (Exception e) {
            return badRequest().body(SimpleAnswer.builder().message(e.toString()).build());
        }
    }

}