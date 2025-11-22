package br.com.technosou.message;

import br.com.technosou.dto.ProposalDTO;
import br.com.technosou.dto.QuotationDTO;
import br.com.technosou.service.OpportunityService;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger logger = LoggerFactory.getLogger(KafkaEvents.class);

    @Inject
    OpportunityService opportunityService;

    @Incoming("proposal")
    @Transactional
    public void receiveProposal(ProposalDTO proposal) {
        logger.info("--------- Recebendo Nova Proposta do Tópico Kafka ---------");
        opportunityService.buildOpportunity(proposal);
    }

    @Incoming("quotation")
    @Blocking
    public void receiveQuotation(QuotationDTO quotation) {
        logger.info("--------- Recebendo Nova Cotação de Moeda do Tópico Kafka ---------");
        opportunityService.saveQuotation(quotation);
    }

}