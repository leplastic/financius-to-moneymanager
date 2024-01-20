package me.andresilva.financius2moneymanager;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class AppConfiguration {

    private List<String> eventTags = new ArrayList<>();
    private String defaultIncomeCategory;
    private String defaultExpenseCategory;

    public boolean isEventTag(String tagTitle) {
        return CollectionUtils.emptyIfNull(eventTags).stream()
                .anyMatch(eventTag -> eventTag.equals(tagTitle));
    }


}