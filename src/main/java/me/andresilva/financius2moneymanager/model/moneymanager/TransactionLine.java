package me.andresilva.financius2moneymanager.model.moneymanager;

import lombok.Data;

@Data
public class TransactionLine {

    //@JsonProperty("Date")
    private String date;

    //@JsonProperty("Account")
    private String account;

    //@JsonProperty("Category")
    private String category;

    //@JsonProperty("Subcategory")
    private String subCategory;

    //@JsonProperty("Note")
    private String note;

    private String amount;
    private TransactionType transactionType;
    private String description;

}
