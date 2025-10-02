package dasturlash.uz.service;

import dasturlash.uz.dto.TransactionDTO;
import dasturlash.uz.enums.TransactionType;
import dasturlash.uz.repository.CardRepository;
import dasturlash.uz.repository.TerminalRepository;
import dasturlash.uz.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TerminalRepository terminalRepository;

    public void createTransaction(Integer cardId,Integer terminalId, Double amount, TransactionType type) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCardId(cardId);
        transactionDTO.setTerminalId(terminalId);
        transactionDTO.setAmount(amount);
        transactionDTO.setTransactionType(type);
        transactionDTO.setCreatedDate(LocalDateTime.now());

        transactionRepository.createTransaction(transactionDTO);
    }
}
