package dasturlash.uz.repository;

import dasturlash.uz.dto.TransactionDTO;
import dasturlash.uz.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createTransaction(TransactionDTO transactionDTO) {
        String sql = "Insert into transactions (card_id, terminal_id, amount, types, created_date) values (?,?,?,?,?)";
        return jdbcTemplate.update(sql, transactionDTO.getCardId(), transactionDTO.getTerminalId(),
                transactionDTO.getAmount(), transactionDTO.getTransactionType().name(), transactionDTO.getCreatedDate());
    }

    public List<TransactionDTO> getTransactionListByProfileId(Integer profileId){
        String sql = "select c.card_number, ter.address, t.amount, t.created_date, t.types\n" +
                "from transactions as t\n" +
                "inner join card as c on c.id = t.card_id\n" +
                "left join terminal as ter on ter.id = t.terminal_id\n" +
                "where t.card_id in (select card_id from profile_card where profile_id = ?)\n" +
                "order by t.created_date desc;";

        RowMapper<TransactionDTO> mapper = (rs, rowNum) -> {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setCardNumber(rs.getString("card_number"));
            transactionDTO.setAddress(rs.getString("address"));
            transactionDTO.setAmount(rs.getDouble("amount"));
            transactionDTO.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            transactionDTO.setTransactionType(TransactionType.valueOf(rs.getString("types")));

            return transactionDTO;
        };
        List<TransactionDTO> list = jdbcTemplate.query(sql, mapper, profileId);
        return list;
    }
}
