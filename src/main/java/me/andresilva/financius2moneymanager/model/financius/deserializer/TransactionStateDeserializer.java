package me.andresilva.financius2moneymanager.model.financius.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import me.andresilva.financius2moneymanager.model.financius.TransactionState;

import java.io.IOException;

public class TransactionStateDeserializer extends JsonDeserializer<TransactionState> {

    @Override
    public TransactionState deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int numeric = jsonParser.getIntValue();
        return TransactionState.getByNumericValue(numeric);
    }
}
