package br.com.technosou.service;

import br.com.technosou.dto.OpportunityDTO;
import br.com.technosou.dto.ProposalDTO;
import br.com.technosou.dto.QuotationDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public interface OpportunityService {

    void buildOpportunity(ProposalDTO proposal);

    void saveQuotation(QuotationDTO quotation);

    List<OpportunityDTO> generateOpportunityData();

}
