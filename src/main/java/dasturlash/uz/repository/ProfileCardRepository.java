package dasturlash.uz.repository;

import dasturlash.uz.dto.CardDTO;
import dasturlash.uz.dto.ProfileCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileCardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(ProfileCardDTO dto) {
        String sql = "Insert into profile_card (card_id, profile_id, visible, created_date) values (?,?,?,?)";
        return jdbcTemplate.update(sql, dto.getCardId(), dto.getProfileId(), true, dto.getCreatedDate());
    }

    public int deleteProfileCard(Integer prifileId, Integer cardId){
        String sql = "update profile_card set visible = false where profile_id =? and card_id =?";
        return jdbcTemplate.update(sql, prifileId, cardId);
    }

    public ProfileCardDTO getProfileCardByCardId(Integer cardId) {
        String sql = "select * from profile_card where card_id = ? and visible = true";
        List<ProfileCardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileCardDTO.class), cardId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<ProfileCardDTO> getProfileCardListByProfileId(Integer profileId) {
        String sql = "select card_number, exp_date, balance, pc.created_date\n" +
                "from profile_card as pc\n" +
                "inner join card as c on c.id = pc.card_id\n" +
                "where profile_id = ? and pc.visible = true";

        RowMapper<ProfileCardDTO> rowMapper = (rs, rowNum) -> {
            CardDTO card = new CardDTO();
            card.setCardNumber(rs.getString("card_number"));
            card.setExpDate(rs.getTimestamp("exp_date").toLocalDateTime().toLocalDate());
            card.setBalance(rs.getDouble("balance"));

            ProfileCardDTO profileCard = new ProfileCardDTO();
            profileCard.setCard(card);
            profileCard.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

            return profileCard;
        };
        List<ProfileCardDTO> list = jdbcTemplate.query(sql, rowMapper, profileId);
        return list;
    }
}
