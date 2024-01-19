package me.andresilva.financius2moneymanager.model.financius;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionState {

    COMPLETE(1),
    PENDING(2);

    private final Integer state;

}
