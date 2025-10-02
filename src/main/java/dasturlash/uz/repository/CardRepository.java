package dasturlash.uz.repository;

import dasturlash.uz.dto.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createWithPSS(CardDTO dto){
        String sql = "insert into card(card_number, exp_date, balance, status, visible, created_date)" +
                "values(?,?,?,?,?,?)";
        PreparedStatementSetter setter = ps -> {
            ps.setString(1, dto.getCardNumber());
            ps.setDate(2, Date.valueOf(dto.getExpDate())); // Todo Time da bug bolishi mumkun
            ps.setDouble(3, dto.getBalance());
            ps.setString(4, dto.getStatus().name());
            ps.setBoolean(5, dto.getVisible());
            ps.setTimestamp(6, Timestamp.valueOf(dto.getCreatedDate()));
        };
        return jdbcTemplate.update(sql, setter);
    }

    public CardDTO getByCardNumber(String cardNumber){
        String sql = "select * from card where card_number = ?";
        List<CardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class), cardNumber);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public List<CardDTO> getList(){
        String sql = "select * from card where visible = true";
        List<CardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class));
        return list;
    }

    public int updateCard(CardDTO cardDTO){
        String sql = "update card set exp_date = ? where card_number = ?";
        return jdbcTemplate.update(sql, cardDTO.getExpDate(), cardDTO.getCardNumber());
    }

    public int changeCardStatus(Integer cardId, String status){
        String sql = "update card set status = ? where id = ?";
        return jdbcTemplate.update(sql, status, cardId);
    }

    public int updateCardBalance(String cardNumber, Double balance) {
        String sql = "update card set balance =? where card_number =?";
        return jdbcTemplate.update(sql, balance, cardNumber);
    }

    public int cardDebit(String cardNumber, Double balance){
        String sql = "update card set balance = balance +? where card_number =?";
        return jdbcTemplate.update(sql, balance, cardNumber);
    }
}
