package me.andresilva.financius2moneymanager.model.financius;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinanciusJson {

    private List<Tag> tags;
    private List<Account> accounts;
    private List<Category> categories;
    private List<Transaction> transactions;

    public Optional<Tag> getTagById(String tagId) {
        return tags.stream()
                .filter(tag -> tagId.equals(tag.getId()))
                .findFirst();
    }

    public Optional<Account> getAccountById(String accountId) {
        return accounts.stream()
                .filter(account -> accountId.equals(account.getId()))
                .findFirst();
    }

    public Optional<Category> getCategoryById(String categoryId) {
        return categories.stream()
                .filter(category -> categoryId.equals(category.getId()))
                .findFirst();
    }

}
