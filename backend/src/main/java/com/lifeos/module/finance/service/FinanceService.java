package com.lifeos.module.finance.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.finance.dto.AccountRequest;
import com.lifeos.module.finance.dto.AccountResponse;
import com.lifeos.module.finance.dto.BudgetRequest;
import com.lifeos.module.finance.dto.BudgetResponse;
import com.lifeos.module.finance.dto.TransactionRequest;
import com.lifeos.module.finance.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Financial Accounts, Budgets, and Transactions operations.
 */
public interface FinanceService {

    AccountResponse createAccount(UUID userId, AccountRequest request);

    AccountResponse getAccountById(UUID userId, UUID id);

    List<AccountResponse> getAccounts(UUID userId);

    AccountResponse updateAccount(UUID userId, UUID id, AccountRequest request);

    void deleteAccount(UUID userId, UUID id);

    BudgetResponse createBudget(UUID userId, BudgetRequest request);

    List<BudgetResponse> getBudgets(UUID userId);

    TransactionResponse createTransaction(UUID userId, TransactionRequest request);

    PagedResponse<TransactionResponse> getTransactions(UUID userId, UUID accountId, PaginationRequest paginationRequest);
}
