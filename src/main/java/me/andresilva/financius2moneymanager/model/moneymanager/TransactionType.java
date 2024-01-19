package me.andresilva.financius2moneymanager.model.moneymanager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    INCOME("Income"),
    EXPENSE("Expense"),
    TRANSFER("Transfer-Out");

    private final String type;

}

