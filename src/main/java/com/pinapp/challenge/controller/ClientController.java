package com.pinapp.challenge.controller;

import com.pinapp.challenge.dto.ClientDTO;
import com.pinapp.challenge.dto.ClientResponseListDTO;
import com.pinapp.challenge.service.ClientService;
import com.pinapp.challenge.validator.ClientValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@Tag(name = "Client Management")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientValidator clientValidator;

    @PostMapping("/creacliente")
    @Operation(
            summary = "Create a new client",
            description = "This endpoint allows you to create a new client with the provided details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client object that needs to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Unexpected error")
            }
    )
    public ResponseEntity<ClientDTO> save(@Valid @RequestBody ClientDTO clientRequest, BindingResult result) {
        clientValidator.validate(clientRequest,result);
        ClientDTO clientDTO = clientService.save(clientRequest);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @GetMapping("/kpideclientes")
    @Operation(
            summary = "Get age statistics of clients",
            description = "This endpoint returns the average age and standard deviation of all clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved age statistics"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Unexpected error")
            }
    )
    public ResponseEntity<Map<String,BigDecimal>> getAgeAverageAndStandardDeviation(){
        HashMap<String, BigDecimal> result = new HashMap<>();
        result.put("Standard Deviation", clientService.getStandardDeviationAge());
        result.put("Age Average", clientService.getAgeAverage());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/listclientes")
    @Operation(
            summary = "List all clients",
            description = "This endpoint retrieves a list of all clients with their details, including the estimated date of death.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clients"),
                    @ApiResponse(responseCode = "404", description = "No clients found"),
                    @ApiResponse(responseCode = "500", description = "Unexpected error")
            }
    )
    public ResponseEntity<List<ClientResponseListDTO>> findAll(){
        List<ClientResponseListDTO> clientDTOList = clientService.findAll();
        if(clientDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clientDTOList,HttpStatus.OK);
        }
    }

}
