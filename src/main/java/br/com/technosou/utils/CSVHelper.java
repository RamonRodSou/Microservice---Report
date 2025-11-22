package br.com.technosou.utils;

import br.com.technosou.dto.OpportunityDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream OpportunityToCSV(List<OpportunityDTO> opportunities) {

        final CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("ID Proposta", "Cliente", "Preço por Tonelada", "Melhor cotação de Moeda");

        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), csvFormat)
        ) {

            for (OpportunityDTO opps : opportunities) {
                    List<String> data = Arrays.asList(
                            String.valueOf(opps.getProposalId()), opps.getCustomer(), String.valueOf(opps.getPriceTonner()),
                            String.valueOf(opps.getLastDollarQuotation()));

                    csvPrinter.printRecord(data);
                }

            csvPrinter.flush();

                return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
                throw new RuntimeException("Fail to import data to CSV file: " + e.getMessage());
        }
    }
}
