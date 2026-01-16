package br.com.technosou.service;

import br.com.technosou.dto.OpportunityDTO;
import br.com.technosou.dto.ProposalDTO;
import br.com.technosou.dto.QuotationDTO;
import br.com.technosou.entity.OpportunityEntity;
import br.com.technosou.entity.QuotationEntity;
import br.com.technosou.repository.OpportunityRepository;
import br.com.technosou.repository.QuotationRepository;
import br.com.technosou.utils.CSVHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OportunityServiceImpl implements OpportunityService {

    @Inject
    private OpportunityRepository opportunityRepository;

    @Inject
    private QuotationRepository quotationRepository;

    @Override
    public void buildOpportunity(ProposalDTO proposal) {

        List<QuotationEntity> quotation = quotationRepository.findAll().list();
        Collections.reverse(quotation);

        OpportunityEntity opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposal.getProposalId());
        opportunity.setCustomer(proposal.getCustomer());
        opportunity.setPriceTonner(proposal.getPriceTonner());
        opportunity.setLastDollarQuotation(quotation.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportunity);
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationDTO quotation) {

        QuotationEntity createQuotation = new QuotationEntity();
        createQuotation.setDate(new Date());
        createQuotation.setCurrencyPrice(quotation.getCurrencyPrice());

        quotationRepository.persist(createQuotation);
    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        List<OpportunityDTO> opportunities = new ArrayList<>();

        opportunityRepository
                .findAll()
                .stream()
                .forEach(it -> {
                    opportunities.add(OpportunityDTO.builder()
                            .proposalId(it.getProposalId())
                            .customer(it.getCustomer())
                            .priceTonner(it.getPriceTonner())
                            .lastDollarQuotation(it.getLastDollarQuotation())
                            .build()
                    );
                });
        return opportunities;
    }

//    @Override
//    public ByteArrayInputStream generateCSVOpportunityReport() {
//
//        List<OpportunityDTO> opportunityList = new ArrayList<>();
//
//         opportunityRepository.findAll().list().forEach(it -> {
//             opportunityList.add(OpportunityDTO
//                     .builder()
//                     .proposalId(it.getProposalId())
//                     .customer(it.getCustomer())
//                     .priceTonner(it.getPriceTonner())
//                     .lastDollarQuotation(it.getLastDollarQuotation())
//                     .build());
//         });
//
//        return CSVHelper.OpportunityToCSV(opportunityList);
//    }
}
