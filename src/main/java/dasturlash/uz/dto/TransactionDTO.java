package dasturlash.uz.dto;

import dasturlash.uz.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO extends TerminalDTO{
    private Integer cardId;
    private Integer terminalId;
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime createdDate;
}
