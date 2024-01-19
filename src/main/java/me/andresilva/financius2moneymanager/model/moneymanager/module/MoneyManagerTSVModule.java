package me.andresilva.financius2moneymanager.model.moneymanager.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import me.andresilva.financius2moneymanager.model.moneymanager.TransactionType;
import me.andresilva.financius2moneymanager.model.moneymanager.serializer.TransactionTypeSerializer;

public class MoneyManagerTSVModule extends SimpleModule {

    public MoneyManagerTSVModule() {
        super();
        addSerializer(TransactionType.class, new TransactionTypeSerializer());
    }
}
