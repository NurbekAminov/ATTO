package dasturlash.uz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTables() {
        String profileSql = "create table if not exists profile(" +
                " id serial primary key," +
                " name varchar(25) not null," +
                " surname varchar(25) not null," +
                " phone varchar(12) unique not null," +
                " pswd varchar not null," +
                " created_date timestamp default now()," +
                " visible bool default true," +
                " status varchar(20) not null," +
                " role varchar(20) not null)";

        String cardSql = "create table if not exists card(\n" +
                "   id serial primary key,\n" +
                "   card_number varchar(16) unique,\n" +
                "   exp_date date not null,\n" +
                "   balance numeric not null,\n" +
                "   status varchar(20) not null,\n" +
                "   visible boolean default true,\n" +
                "   created_date timestamp not null default now() )";

        String profileCardSql = "create table if not exists profile_card(\n" +
                "   id serial primary key,\n" +
                "   card_id integer not null,\n" +
                "   profile_id integer not null,\n" +
                "   visible boolean default true,\n" +
                "   created_date timestamp not null default now() )";

        jdbcTemplate.execute(profileCardSql);
        jdbcTemplate.execute(cardSql);
        jdbcTemplate.execute(profileSql);
    }

}
