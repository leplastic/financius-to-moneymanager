package me.andresilva.financius2moneymanager.model.financius;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.andresilva.financius2moneymanager.exception.FinanciusToMoneyManagerException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    EXPENSE(1),
    INCOME(2),
    TRANSFER(3);

    private final int type;

    public static TransactionType findTransactionType(int numericCode) {
        return Arrays.stream(values())
                .filter(transactionType -> transactionType.getType() == numericCode)
                .findFirst()
                .orElseThrow(() -> new FinanciusToMoneyManagerException("Invalid Financius transaction type " + numericCode));
    }

}
