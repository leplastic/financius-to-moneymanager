package me.andresilva.financius2moneymanager.model.financius.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import me.andresilva.financius2moneymanager.model.financius.TransactionType;
import me.andresilva.financius2moneymanager.model.financius.deserializer.TransactionTypeDeserializer;

public class FinanciusJsonModule extends SimpleModule {

    public FinanciusJsonModule() {
        super();
        addDeserializer(TransactionType.class, new TransactionTypeDeserializer());
    }
}
