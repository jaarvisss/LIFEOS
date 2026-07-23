package com.lifeos.module.finance.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.finance.dto.AccountRequest;
import com.lifeos.module.finance.dto.AccountResponse;
import com.lifeos.module.finance.dto.BudgetRequest;
import com.lifeos.module.finance.dto.BudgetResponse;
import com.lifeos.module.finance.dto.TransactionRequest;
import com.lifeos.module.finance.dto.TransactionResponse;
import com.lifeos.module.finance.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Personal Finance (Accounts, Budgets, Transactions).
 */
@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
@Tag(name = "Personal Finance", description = "Endpoints for accounts, budgets, expenses, and income tracking")
public class FinanceController {

    private final FinanceService financeService;

    // --- Accounts ---

    @PostMapping("/accounts")
    @Operation(summary = "Create Financial Account", description = "Creates a new financial bank/credit/cash account.")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(financeService.createAccount(userId, request), "Account created successfully");
    }

    @GetMapping("/accounts")
    @Operation(summary = "List Financial Accounts", description = "Retrieves user's financial accounts.")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccounts() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(financeService.getAccounts(userId));
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Get Account by ID", description = "Retrieves account details.")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(financeService.getAccountById(userId, id));
    }

    @PutMapping("/accounts/{id}")
    @Operation(summary = "Update Account", description = "Updates account attributes.")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable UUID id,
            @Valid @RequestBody AccountRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(financeService.updateAccount(userId, id, request), "Account updated successfully");
    }

    @DeleteMapping("/accounts/{id}")
    @Operation(summary = "Delete Account", description = "Soft-deletes a financial account.")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        financeService.deleteAccount(userId, id);
        return ResponseBuilder.ok(null, "Account deleted successfully");
    }

    // --- Budgets ---

    @PostMapping("/budgets")
    @Operation(summary = "Create Budget", description = "Allocates a spending budget for a category.")
    public ResponseEntity<ApiResponse<BudgetResponse>> createBudget(@Valid @RequestBody BudgetRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(financeService.createBudget(userId, request), "Budget created successfully");
    }

    @GetMapping("/budgets")
    @Operation(summary = "List Budgets", description = "Retrieves spending budgets.")
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getBudgets() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(financeService.getBudgets(userId));
    }

    // --- Transactions ---

    @PostMapping("/transactions")
    @Operation(summary = "Create Transaction", description = "Logs income or expense transaction and adjusts account balance.")
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(financeService.createTransaction(userId, request), "Transaction recorded successfully");
    }

    @GetMapping("/transactions")
    @Operation(summary = "List Transactions", description = "Retrieves paginated financial transaction history.")
    public ResponseEntity<ApiResponse<PagedResponse<TransactionResponse>>> getTransactions(
            @RequestParam(value = "accountId", required = false) UUID accountId,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(financeService.getTransactions(userId, accountId, paginationRequest));
    }
}
