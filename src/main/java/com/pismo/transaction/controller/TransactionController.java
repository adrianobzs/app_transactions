package com.pismo.transaction.controller;

import com.pismo.transaction.dto.request.TransactionRequestDTO;
import com.pismo.transaction.dto.response.TransactionResponseDTO;
import com.pismo.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new Transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created with Success."),
            @ApiResponse(responseCode = "400", description = "Not valid data."),
            @ApiResponse(responseCode = "404", description = "Account ou TransactionType NOT FOUND.")
    })
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO requestDTO) {
        System.out.println(requestDTO);
        TransactionResponseDTO response = transactionService.createTransaction(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Search Transaction by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Transaction Not Found.")
    })
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable Long transactionId) {
        TransactionResponseDTO response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }
}