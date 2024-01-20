package me.andresilva.financius2moneymanager.model.financius.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import me.andresilva.financius2moneymanager.model.financius.ModelState;
import me.andresilva.financius2moneymanager.model.financius.TransactionState;
import me.andresilva.financius2moneymanager.model.financius.TransactionType;
import me.andresilva.financius2moneymanager.model.financius.deserializer.ModelStateDeserializer;
import me.andresilva.financius2moneymanager.model.financius.deserializer.TransactionStateDeserializer;
import me.andresilva.financius2moneymanager.model.financius.deserializer.TransactionTypeDeserializer;

public class FinanciusJsonModule extends SimpleModule {

    public FinanciusJsonModule() {
        super();
        addDeserializer(TransactionType.class, new TransactionTypeDeserializer());
        addDeserializer(ModelState.class, new ModelStateDeserializer());
        addDeserializer(TransactionState.class, new TransactionStateDeserializer());
    }
}
