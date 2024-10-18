package com.pinapp.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Client DTO")
public class ClientDTO {
    @Schema(hidden = true)
    private Long id;
    @Schema(example = "John")
    private String name;
    @Schema(example = "Doe")
    private String lastName;

    @Min(value = 0, message = "{error.age.min}")
    @Max(value = 120, message = "{error.age.max}")
    @Schema(example = "27")
    private Integer age;

    @NotNull(message = "{error.birthday.null}")
    @Past(message = "{error.birthday.past}}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(
            type = "string",
            example = "22-11-1996",
            pattern = "ddMMyyyy"
    )
    private Date birthday;
}
