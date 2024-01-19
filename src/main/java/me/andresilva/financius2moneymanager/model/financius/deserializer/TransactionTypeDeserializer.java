package me.andresilva.financius2moneymanager.model.financius.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import me.andresilva.financius2moneymanager.model.financius.TransactionType;

import java.io.IOException;

public class TransactionTypeDeserializer extends JsonDeserializer<TransactionType> {

    @Override
    public TransactionType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int numeric = jsonParser.getIntValue();
        return TransactionType.findTransactionType(numeric);
    }
}
