package com.pinapp.challenge.repository;

import com.pinapp.challenge.model.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveClient() throws Exception {

        //Given
        Date birthday = new SimpleDateFormat("dd-MM-yyyy").parse("22-11-1996");
        Client client = new Client(null, "John", "Doe", 27, new Date(),birthday );
       
        //When
        Client savedClient = clientRepository.save(client);
        //Then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo("John");
        assertThat(savedClient.getLastName()).isEqualTo("Doe");
        assertThat(savedClient.getAge()).isEqualTo(27);
        assertThat(savedClient.getBirthday()).isEqualTo(birthday);
    }

    @Test
    public void shouldGetAverageAge(){

        //Given
        clientRepository.save(new Client(null, "John", "Doe", 30, new Date(), new Date()));
        clientRepository.save(new Client(null, "Jane", "Smith", 40, new Date(),new Date()));
        clientRepository.save(new Client(null, "Alice", "Johnson", 50, new Date(),new Date()));

        BigDecimal calculatedAverageAge = BigDecimal.valueOf((30 + 40 + 50) / 3).setScale(1);

        //When
        Optional<BigDecimal> averageAge = clientRepository.getAgeAverage();

        //Then
        assertThat(averageAge.isPresent()).isTrue();
        assertThat(averageAge.get()).isEqualTo(calculatedAverageAge);
    }

    @Test
    public void shouldGetStandardDeviationAge() {

        //Given
        clientRepository.save(new Client(null, "John", "Doe", 30, new Date(), new Date()));
        clientRepository.save(new Client(null, "Jane", "Smith", 40, new Date(), new Date()));
        clientRepository.save(new Client(null, "Alice", "Johnson", 50, new Date(), new Date()));

        BigDecimal calculatedStdDev = BigDecimal.valueOf(8.16);
        //When
        Optional<BigDecimal> standardDeviation = clientRepository.getStandardDeviationAge();

        //Then
        assertThat(standardDeviation).isPresent();
        standardDeviation.ifPresent(std -> {
            BigDecimal truncatedStd = std.setScale(2, RoundingMode.DOWN);
            assertThat(truncatedStd).isEqualTo(calculatedStdDev);
        });
    }
}
