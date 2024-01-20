package me.andresilva.financius2moneymanager;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import me.andresilva.financius2moneymanager.mapper.TransactionLineMapper;
import me.andresilva.financius2moneymanager.model.financius.FinanciusJson;
import me.andresilva.financius2moneymanager.model.financius.module.FinanciusJsonModule;
import me.andresilva.financius2moneymanager.model.moneymanager.TransactionLine;
import me.andresilva.financius2moneymanager.model.moneymanager.module.MoneyManagerTSVModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private TransactionLineMapper transactionLineMapper;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws IOException {

        // import
        InputStream inJson = Files.newInputStream(Paths.get("financius.json"));
        FinanciusJson financiusJson = new ObjectMapper()
                .registerModules(new FinanciusJsonModule())
                .readValue(inJson, FinanciusJson.class);

        // transform
        List<TransactionLine> transactionLines = transactionLineMapper.financiusModelToMoneyManagerTransactionLineList(financiusJson);

        // export
        OutputStream output = Files.newOutputStream(Path.of("exported.tsv"));
        CsvSchema schema = CsvSchema.builder().setUseHeader(true)
                .addColumn("date")
                .addColumn("account")
                .addColumn("category")
                .addColumn("subCategory")
                .addColumn("note")
                .addColumn("amount")
                .addColumn("transactionType")
                .addColumn("description")
                .setUseHeader(true)
                .setColumnSeparator('\t')
                .disableQuoteChar()
                .build();

        new CsvMapper()
                .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .registerModules(new MoneyManagerTSVModule())
                .writerFor(TransactionLine.class)
                .with(schema)
                .writeValues(output)
                .writeAll(transactionLines);
    }
}