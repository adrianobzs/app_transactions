package com.pismo.transaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {

    @NotBlank(message = "Document Number is mandatory.")
    @Pattern(regexp = "^\\d+$", message = "Document Number must contain only digits.")
    @Size(min = 1, max = 20, message = "Document Number must be between 1 and 20 digits.")
    private String documentNumber;
}
