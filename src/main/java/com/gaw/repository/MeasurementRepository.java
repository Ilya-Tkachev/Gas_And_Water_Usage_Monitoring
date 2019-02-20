package com.gaw.repository;

import com.gaw.model.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    @Query("select m from Measurement m where m.user.id = (select u.id from User u where u.email = ?1)")
    List<Measurement> findByUserName(String userName);

}