package com.pinapp.challenge.validator;

import com.pinapp.challenge.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;

import static com.pinapp.challenge.utils.DateHelper.calculateAge;

@Component
public class ClientValidator implements Validator {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors result) {
        ClientDTO client = (ClientDTO) target;
        StringJoiner errorMessages = new StringJoiner(", ");
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
        }

        if(client.getAge() != null && client.getBirthday() != null){
            Integer calculatedAge = calculateAge(client.getBirthday());
            if(!calculatedAge.equals(client.getAge())){
                errorMessages.add(messageSource.getMessage("error.age.mismatch", null, Locale.getDefault()));
            }
        }

        if(errorMessages.length() > 0){
            throw new IllegalArgumentException(errorMessages.toString());
        }

    }

}
