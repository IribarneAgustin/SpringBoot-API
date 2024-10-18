package com.pinapp.challenge.service;

import com.pinapp.challenge.dto.ClientDTO;
import com.pinapp.challenge.dto.ClientResponseListDTO;
import com.pinapp.challenge.model.Client;
import com.pinapp.challenge.repository.ClientRepository;
import com.pinapp.challenge.utils.DateHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    public static final Integer LIFE_EXPECTANCY = 80;

    @Transactional
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setPossibleDeath(DateHelper.addYears(client.getBirthday(), LIFE_EXPECTANCY));
        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    public List<ClientResponseListDTO> findAll(){
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream().map(client -> { return modelMapper.map(client, ClientResponseListDTO.class);}).toList();
    }

    public BigDecimal getStandardDeviationAge() {
        return clientRepository.getStandardDeviationAge().orElse(BigDecimal.ZERO);
    }

    public BigDecimal getAgeAverage() {
        return clientRepository.getAgeAverage().orElse(BigDecimal.ZERO);
    }

}
