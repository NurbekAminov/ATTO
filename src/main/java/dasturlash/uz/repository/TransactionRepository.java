package dasturlash.uz.repository;

import dasturlash.uz.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createTransaction(TransactionDTO transactionDTO) {
        String sql = "Insert into transactions (card_id, terminal_id, amount, types, created_date) values (?,?,?,?,?)";
        return jdbcTemplate.update(sql, transactionDTO.getCardId(), transactionDTO.getTerminalId(),
                transactionDTO.getAmount(), transactionDTO.getTransactionType().name(), transactionDTO.getCreatedDate());
    }
}
