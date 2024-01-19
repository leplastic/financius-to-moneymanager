package me.andresilva.financius2moneymanager.mapper;

import me.andresilva.financius2moneymanager.AppConfiguration;
import me.andresilva.financius2moneymanager.exception.FinanciusToMoneyManagerException;
import me.andresilva.financius2moneymanager.model.financius.*;
import me.andresilva.financius2moneymanager.model.moneymanager.TransactionLine;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Mapper(componentModel = "spring")
public abstract class TransactionLineMapper {

    @Autowired
    private AppConfiguration configuration;

    public List<TransactionLine> financiusModelToMoneyManagerTransactionLineList(FinanciusJson financiusJson) {
        return financiusJson.getTransactions().stream()
                .map(transaction -> financiusTransactionToMoneyManagerTransactionLine(transaction, financiusJson))
                .toList();
    }

    @Mapping(target = "date", source = "date", qualifiedByName = "mapTransactionLineDate")
    @Mapping(target = "account", source = "transaction", qualifiedByName = "mapAccountIdToAccount")
    @Mapping(target = "category", source = "transaction", qualifiedByName = "mapMainCategory")
    @Mapping(target = "subCategory", source = "tagIds", qualifiedByName = "mapSubCategory")
    @Mapping(target = "note", source = "transaction", qualifiedByName = "mapNote")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "formatAmount")
    @Mapping(target = "transactionType", source = "transactionType")
    @Mapping(target = "description", source = "tagIds", qualifiedByName = "mapDetails")
    public abstract TransactionLine financiusTransactionToMoneyManagerTransactionLine(Transaction transaction, @Context FinanciusJson financiusJson);

    protected abstract me.andresilva.financius2moneymanager.model.moneymanager.TransactionType financiusTransactionTypeToMMTransactionType(TransactionType transactionType);

    @Named("formatAmount")
    protected String formatAmount(Long amount) {
        if (amount == null) {
            return null;
        }
        return new BigDecimal(amount).multiply(BigDecimal.valueOf(0.01)).toPlainString();
    }

    @Named("mapMainCategory")
    protected String mapMainCategory(Transaction transaction, @Context FinanciusJson financiusJson) {
        if (transaction.getTransactionType() == TransactionType.TRANSFER) {
            return financiusJson.getAccountById(transaction.getAccountToId())
                    .map(Account::getTitle)
                    .orElseThrow(() -> new FinanciusToMoneyManagerException("No destination account for transaction id " + transaction.getId()));
        }

        return Optional.ofNullable(transaction.getCategoryId())
                .map(categoryId -> financiusJson.getCategoryById(categoryId).orElseThrow(() -> new FinanciusToMoneyManagerException("No matching category for category id " + transaction.getCategoryId())))
                .map(Category::getTitle)
                .orElseGet(() -> switch (transaction.getTransactionType()) {
                    case EXPENSE -> configuration.getDefaultExpenseCategory();
                    case INCOME -> configuration.getDefaultIncomeCategory();
                    default -> null;
                });
    }

    @Named("mapTransactionLineDate")
    protected String mapTransactionLineDate(Long timestamp) {
        return Optional.ofNullable(timestamp)
                .map(date -> DateTimeFormatter.ofPattern("MM/dd/yyyy").format(Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())))
                .orElse(null);
    }

    @Named("mapSubCategory")
    protected String mapSubcategory(List<String> tagIds, @Context FinanciusJson financiusJson) {
        return tagIds.stream()
                .map(tagId -> financiusJson.getTagById(tagId).orElseThrow(() -> new FinanciusToMoneyManagerException("Unexpected tag id " + tagId)))
                .map(tag -> StringUtils.trim(tag.getTitle()))
                .filter(not(configuration::isEventTag))
                .findFirst()
                .orElse(null);
    }

    @Named("mapNote")
    protected String mapNote(Transaction transaction) {
        String strippedNote = StringUtils.trim(StringUtils.replace(transaction.getNote(), "\n", " "));

        // include in reports
        if (Boolean.FALSE.equals(transaction.getIncludeInReports())) {
            strippedNote = "[Excluded from reports] " + strippedNote;
        }

        return strippedNote;
    }

    @Named("mapDetails")
    protected String mapDetails(List<String> tagIds, @Context FinanciusJson financiusJson) {
        if (CollectionUtils.isEmpty(configuration.getEventTags()) || CollectionUtils.isEmpty(tagIds)) {
            return null;
        }

        return StringUtils.trimToNull(tagIds.stream()
                .map(tagId -> financiusJson.getTagById(tagId).orElseThrow(() -> new FinanciusToMoneyManagerException("Unexpected tag id " + tagId)))
                .map(Tag::getTitle)
                .filter(configuration::isEventTag)
                .map(tag -> "#" + StringUtils.replaceChars(tag, " ", "-"))
                .collect(Collectors.joining(", ")));
    }

    @Named("mapAccountIdToAccount")
    protected String mapAccountIdToAccount(Transaction transaction, @Context FinanciusJson financiusJson) {
        String accountId = transaction.getAccountToId();
        if (transaction.isExpenseOrTransfer()) {
            accountId = transaction.getAccountFromId();
        }

        return financiusJson.getAccountById(accountId)
                .map(account -> StringUtils.trim(account.getTitle()))
                .orElseThrow(FinanciusToMoneyManagerException::new);
    }
}
