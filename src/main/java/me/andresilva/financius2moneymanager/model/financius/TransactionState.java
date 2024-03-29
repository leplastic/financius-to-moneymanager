package me.andresilva.financius2moneymanager.model.financius;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.andresilva.financius2moneymanager.exception.FinanciusToMoneyManagerException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TransactionState {

    COMPLETE(1),
    PENDING(2);

    private final int state;

    public static TransactionState getByNumericValue(int numericCode) {
        return Arrays.stream(values())
                .filter(value -> value.getState() == numericCode)
                .findFirst()
                .orElseThrow(() -> new FinanciusToMoneyManagerException("Invalid Financius Transaction state " + numericCode));
    }

}
