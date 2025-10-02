package dasturlash.uz.service;

import dasturlash.uz.dto.TransactionDTO;
import dasturlash.uz.enums.TransactionType;
import dasturlash.uz.repository.CardRepository;
import dasturlash.uz.repository.TerminalRepository;
import dasturlash.uz.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        System.out.println("Operation completed");
    }
    public void getTransactionsList(Integer profileId){
        List<TransactionDTO> transactionList = transactionRepository.getTransactionListByProfileId(profileId);
        if (transactionList.isEmpty()){
            System.out.println("Transactions not found");
            return;
        }

        System.out.printf("---------------------------------------------------------------------------------------%n");
        System.out.printf("                          Transactions List                                            %n");
        System.out.printf("---------------------------------------------------------------------------------------%n");
        System.out.printf("| %-16s | %-12s | %-9s | %-18s | %-8s |%n", "CardNumber", "Address", "balance", "CreatedDate", "Transaction type");
        System.out.printf("---------------------------------------------------------------------------------------%n");
        for (TransactionDTO transactionDTO : transactionList) {
            System.out.printf("| %-16s | %-12s | %-9s | %-18s | %-8s |%n",
                    transactionDTO.getCardNumber(), transactionDTO.getAddress(), transactionDTO.getAmount(),
                    transactionDTO.getCreatedDate(), transactionDTO.getTransactionType().name());
        }
        System.out.printf("---------------------------------------------------------------------------------------%n");
    }
}
