package com.pinapp.challenge.repository;

import com.pinapp.challenge.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT STDDEV_POP(age) from Client")
    Optional<BigDecimal> getStandardDeviationAge();

    @Query("SELECT AVG(age) from Client")
    Optional<BigDecimal> getAgeAverage();
}
