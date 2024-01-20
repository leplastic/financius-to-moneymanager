package me.andresilva.financius2moneymanager.model.financius;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.andresilva.financius2moneymanager.exception.FinanciusToMoneyManagerException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ModelState {

    NORMAL(1),
    DELETED(2),
    DELETED_UNDO(3);

    private final int state;

    public static ModelState getByNumericValue(int numericCode) {
        return Arrays.stream(values())
                .filter(value -> value.getState() == numericCode)
                .findFirst()
                .orElseThrow(() -> new FinanciusToMoneyManagerException("Invalid Financius Model state " + numericCode));
    }

}