package com.pismo.transaction.controller;

import com.pismo.transaction.dto.request.AccountRequestDTO;
import com.pismo.transaction.dto.response.AccountResponseDTO;
import com.pismo.transaction.dto.response.AccountResponseDetailDTO;
import com.pismo.transaction.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create a new Account", description = "Create a new Account based on the DocumentNumber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Account with success!"),
            @ApiResponse(responseCode = "400", description = "Invalid Data."),
            @ApiResponse(responseCode = "409", description = "Document already exists.")
    })
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO requestDTO) {
        AccountResponseDTO response = accountService.createAccount(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Search by Id", description = "Return the Account's data based on its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Account."),
            @ApiResponse(responseCode = "404", description = "Not Found Account.")
    })
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable UUID accountId) {
        AccountResponseDTO response = accountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }
}