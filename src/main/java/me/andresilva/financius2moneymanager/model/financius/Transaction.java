package me.andresilva.financius2moneymanager.model.financius;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Transaction {

    private String id;
    private String accountFromId;
    private String accountToId;
    private String categoryId;
    private List<String> tagIds;
    private Long date;
    private Long amount;
    private String note;
    private TransactionType transactionType;
    private Boolean includeInReports;

    public boolean isExpenseOrTransfer() {
        return transactionType == TransactionType.TRANSFER || transactionType == TransactionType.EXPENSE;
    }

}
