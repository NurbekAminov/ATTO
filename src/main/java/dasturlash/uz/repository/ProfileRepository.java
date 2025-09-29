package dasturlash.uz.repository;

import dasturlash.uz.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(ProfileDTO dto) {
        String sql = "insert into profile(name, surname, phone, created_date, visible, status, role) " +
                "values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, dto.getName(), dto.getSurname(), dto.getPhone(),
                dto.getPswd(), dto.getCreatedDate(), dto.getVisible(), dto.getStatus(), dto.getRole());
    }

    public int createWithPSS(ProfileDTO dto) {
        String sql = "insert into profile(name, surname, phone, pswd, created_date, visible, status, role) " +
                "values(?,?,?,?,?,?,?,?)";
        PreparedStatementSetter setter = ps -> {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getSurname());
            ps.setString(3, dto.getPhone());
            ps.setString(4, dto.getPswd());
            ps.setTimestamp(5, Timestamp.valueOf(dto.getCreatedDate()));
            ps.setBoolean(6, dto.getVisible());
            ps.setString(7, dto.getStatus().name());
            ps.setString(8, dto.getRole().name());
        };
        return jdbcTemplate.update(sql, setter);
    }

    public ProfileDTO getByPhone(String phone){
        String sql = "select * from profile where phone = ?";
        List<ProfileDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), phone);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    
}