package me.andresilva.financius2moneymanager.model.financius.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import me.andresilva.financius2moneymanager.model.financius.ModelState;

import java.io.IOException;

public class ModelStateDeserializer extends JsonDeserializer<ModelState> {

    @Override
    public ModelState deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int numeric = jsonParser.getIntValue();
        return ModelState.getByNumericValue(numeric);
    }
}
