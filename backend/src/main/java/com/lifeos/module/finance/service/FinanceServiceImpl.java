package com.lifeos.module.finance.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.finance.dto.AccountRequest;
import com.lifeos.module.finance.dto.AccountResponse;
import com.lifeos.module.finance.dto.BudgetRequest;
import com.lifeos.module.finance.dto.BudgetResponse;
import com.lifeos.module.finance.dto.TransactionRequest;
import com.lifeos.module.finance.dto.TransactionResponse;
import com.lifeos.module.finance.entity.AccountEntity;
import com.lifeos.module.finance.entity.BudgetEntity;
import com.lifeos.module.finance.entity.TransactionEntity;
import com.lifeos.module.finance.mapper.FinanceMapper;
import com.lifeos.module.finance.repository.AccountRepository;
import com.lifeos.module.finance.repository.BudgetRepository;
import com.lifeos.module.finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of FinanceService interface.
 */
@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final FinanceMapper financeMapper;

    @Override
    @Transactional
    public AccountResponse createAccount(UUID userId, AccountRequest request) {
        AccountEntity entity = financeMapper.toEntity(request);
        entity.setUserId(userId);
        entity = accountRepository.save(entity);
        return financeMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(UUID userId, UUID id) {
        AccountEntity entity = accountRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        return financeMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccounts(UUID userId) {
        return financeMapper.toAccountDtoList(accountRepository.findByUserIdAndIsDeletedFalse(userId));
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(UUID userId, UUID id, AccountRequest request) {
        AccountEntity entity = accountRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        financeMapper.updateEntityFromDto(request, entity);
        entity = accountRepository.save(entity);
        return financeMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, UUID id) {
        AccountEntity entity = accountRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        entity.markDeleted();
        accountRepository.save(entity);
    }

    @Override
    @Transactional
    public BudgetResponse createBudget(UUID userId, BudgetRequest request) {
        BudgetEntity entity = financeMapper.toEntity(request);
        entity.setUserId(userId);
        entity = budgetRepository.save(entity);
        return financeMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetResponse> getBudgets(UUID userId) {
        return financeMapper.toBudgetDtoList(budgetRepository.findByUserIdAndIsDeletedFalse(userId));
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(UUID userId, TransactionRequest request) {
        AccountEntity account = accountRepository.findByIdAndUserIdAndIsDeletedFalse(request.accountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.accountId()));

        TransactionEntity transaction = financeMapper.toEntity(request);
        transaction.setUserId(userId);
        if (transaction.getTransactionTimestamp() == null) {
            transaction.setTransactionTimestamp(Instant.now());
        }

        // Balance adjustment
        if ("INCOME".equalsIgnoreCase(request.transactionType())) {
            account.setCurrentBalance(account.getCurrentBalance().add(request.amount()));
        } else if ("EXPENSE".equalsIgnoreCase(request.transactionType())) {
            account.setCurrentBalance(account.getCurrentBalance().subtract(request.amount()));
        }
        accountRepository.save(account);

        transaction = transactionRepository.save(transaction);
        return financeMapper.toDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TransactionResponse> getTransactions(UUID userId, UUID accountId, PaginationRequest paginationRequest) {
        Page<TransactionEntity> page;
        if (accountId != null) {
            page = transactionRepository.findByUserIdAndAccountIdAndIsDeletedFalse(userId, accountId, paginationRequest.toPageable());
        } else {
            page = transactionRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        }
        return PagedResponse.from(page.map(financeMapper::toDto));
    }
}
