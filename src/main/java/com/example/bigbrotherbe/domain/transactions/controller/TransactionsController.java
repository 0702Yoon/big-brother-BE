package com.example.bigbrotherbe.domain.transactions.controller;

import com.amazonaws.Response;
import com.example.bigbrotherbe.domain.meetings.dto.request.MeetingsUpdateRequest;
import com.example.bigbrotherbe.domain.transactions.dto.request.TransactionsUpdateRequest;
import com.example.bigbrotherbe.domain.transactions.dto.response.TransactionsResponse;
import com.example.bigbrotherbe.domain.transactions.service.TransactionsService;
import com.example.bigbrotherbe.domain.transactions.service.TransactionsServiceImpl;
import com.example.bigbrotherbe.global.exception.response.ApiResponse;
import com.example.bigbrotherbe.global.ocr.dto.OcrDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static com.example.bigbrotherbe.global.exception.enums.SuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionsResponse>>> getTransactions(@RequestParam("affiliationId") Long affiliationId,
                                                                                   @RequestParam("year") int year,
                                                                                   @RequestParam("month") int month) {
        List<TransactionsResponse> transactionsList = transactionsService.getTransactionsWithMonth(year, month, affiliationId);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, transactionsList));
    }
}
