package com.lifeos.module.finance.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.finance.dto.AccountRequest;
import com.lifeos.module.finance.dto.AccountResponse;
import com.lifeos.module.finance.dto.BudgetRequest;
import com.lifeos.module.finance.dto.BudgetResponse;
import com.lifeos.module.finance.dto.TransactionRequest;
import com.lifeos.module.finance.dto.TransactionResponse;
import com.lifeos.module.finance.entity.AccountEntity;
import com.lifeos.module.finance.entity.BudgetEntity;
import com.lifeos.module.finance.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Finance entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface FinanceMapper {

    AccountResponse toDto(AccountEntity entity);

    AccountEntity toEntity(AccountRequest request);

    List<AccountResponse> toAccountDtoList(List<AccountEntity> entityList);

    void updateEntityFromDto(AccountRequest request, @MappingTarget AccountEntity entity);

    BudgetResponse toDto(BudgetEntity entity);

    BudgetEntity toEntity(BudgetRequest request);

    List<BudgetResponse> toBudgetDtoList(List<BudgetEntity> entityList);

    void updateEntityFromDto(BudgetRequest request, @MappingTarget BudgetEntity entity);

    TransactionResponse toDto(TransactionEntity entity);

    TransactionEntity toEntity(TransactionRequest request);

    List<TransactionResponse> toTransactionDtoList(List<TransactionEntity> entityList);
}
