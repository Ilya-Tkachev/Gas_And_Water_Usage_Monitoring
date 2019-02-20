package com.gaw.service;

import com.gaw.model.entity.Measurement;
import com.gaw.model.view.MeasurementView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MeasurementServiceTest {

    @Autowired
    MeasurementService measurementService;

    @Test
    void getByUserNameTest1() {
        //Given
        String randomName = "";

        //When
        //Then
        assertThrows(RuntimeException.class, () -> measurementService.getByUserName(randomName));
    }

    @Test
    void getByUserNameTest2() {
        //Given
        String existingUser = "email@gmail.com";

        //When
        Map<String, List<MeasurementView>> results = measurementService.getByUserName(existingUser);

        //Then
        assertEquals(3, results.size());
    }

    @Test
    void submitTest1() {
        //Given
        MeasurementView measurementView1 = MeasurementView.builder().amount(0.0).measurementName("GAS").build();
        MeasurementView measurementView2 = MeasurementView.builder().amount(0.132).measurementName("HOT_WATER").build();
        MeasurementView measurementView3 = MeasurementView.builder().amount(0.788).measurementName("HOT_WATER").build();
        List<MeasurementView> appointmentViews = Arrays.asList(measurementView1, measurementView2, measurementView3);
        String email = "email@gmail.com";

        //When
        //Then
        assertThrows(RuntimeException.class, () -> measurementService.submit(appointmentViews, email));
    }

    @Test
    void submitTest2() {
        //Given
        MeasurementView measurementView1 = MeasurementView.builder().amount(0.0).measurementName("NOT_EXISTING_TYPE").build();
        List<MeasurementView> appointmentViews = Collections.singletonList(measurementView1);
        String email = "email@gmail.com";

        //When
        //Then
        assertThrows(RuntimeException.class, () -> measurementService.submit(appointmentViews, email));
    }

    @Test
    void submitTest3() {
        //Given
        String email = "notExistingUser";

        //When
        //Then
        assertThrows(RuntimeException.class, () -> measurementService.submit(null, email));
    }

    @Test
    void submitTest4() {
        //Given
        double gasAmount = 0.0;
        double hotWaterAmount = 0.132;
        double coldWaterAmount = 0.788;
        MeasurementView measurementView1 = MeasurementView.builder().amount(gasAmount).measurementName("GAS").build();
        MeasurementView measurementView2 = MeasurementView.builder().amount(hotWaterAmount).measurementName("HOT_WATER").build();
        MeasurementView measurementView3 = MeasurementView.builder().amount(coldWaterAmount).measurementName("COLD_WATER").build();
        List<MeasurementView> appointmentViews = Arrays.asList(measurementView1, measurementView2, measurementView3);
        String email = "email@gmail.com";

        //When
        Map<Long, List<Measurement>> savedMeasures = measurementService.submit(appointmentViews, email);

        //Then
        assertEquals(3, savedMeasures.size());
        assertEquals(1, savedMeasures.get(1L).size());
        assertEquals(new Double(gasAmount), savedMeasures.get(1L).get(0).getAmount());
        assertEquals(1, savedMeasures.get(2L).size());
        assertEquals(new Double(hotWaterAmount), savedMeasures.get(2L).get(0).getAmount());
        assertEquals(1, savedMeasures.get(3L).size());
        assertEquals(new Double(coldWaterAmount), savedMeasures.get(3L).get(0).getAmount());
    }

}