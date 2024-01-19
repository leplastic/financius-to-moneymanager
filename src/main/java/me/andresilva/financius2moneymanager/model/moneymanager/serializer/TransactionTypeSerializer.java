package me.andresilva.financius2moneymanager.model.moneymanager.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.andresilva.financius2moneymanager.model.moneymanager.TransactionType;

import java.io.IOException;

public class TransactionTypeSerializer extends JsonSerializer<TransactionType> {

    @Override
    public void serialize(TransactionType transactionType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(transactionType.getType());
    }

}
