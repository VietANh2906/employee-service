package com.example.employee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record Employee(
        @NotNull @Positive Long id,
        @NotBlank @Size(max = 100) String name,
        @NotNull @DecimalMin("0.00") @Digits(integer = 13, fraction = 2)
        BigDecimal salary
) {}