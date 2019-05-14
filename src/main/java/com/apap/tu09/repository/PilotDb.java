package com.apap.tu09.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tu09.model.PilotModel;

/**
 * PilotDb
 */
@Repository
public interface PilotDb extends JpaRepository<PilotModel, Long> {
    Optional<PilotModel> findByLicenseNumber(String licenseNumber);

    void deleteByLicenseNumber(String licenseNumber);
}