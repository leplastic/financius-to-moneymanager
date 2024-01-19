package me.andresilva.financius2moneymanager.model.financius;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

    private String id;
    private String title;

}
