package me.andresilva.financius2moneymanager.model.moneymanager;

import lombok.Data;

@Data
public class TransactionLine {

    private String date;
    private String account;
    private String category;
    private String subCategory;
    private String note;
    private String amount;
    private TransactionType transactionType;
    private String description;

}
