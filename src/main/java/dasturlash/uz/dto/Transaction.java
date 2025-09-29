package dasturlash.uz.dto;

import dasturlash.uz.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    private CardDTO card_number;
    private Float amount;
    private Terminal terminal_code;
    private TransactionType type;
    private LocalDateTime created_date;
}
