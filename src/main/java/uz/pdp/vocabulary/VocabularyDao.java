package uz.pdp.vocabulary;

import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@Component
public class VocabularyDao {
    private final JdbcTemplate jdbcTemplate;

    public VocabularyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(@NonNull VocabularyCreateDto dto, Long userID) {
        var sql = "insert into vocabulary (word,translations,synonyms,examples,userID) values (?,?,?,?,?);";
        jdbcTemplate.update(con ->
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dto.word());
            ps.setArray(2, con.createArrayOf("varchar", dto.translations().toArray()));
            ps.setArray(3, con.createArrayOf("varchar", dto.synonyms().toArray()));
            ps.setArray(4, con.createArrayOf("varchar", dto.examples().toArray()));
            ps.setLong(5, userID);
            return ps;
        });
    }


    public List<Vocabulary> findByUserID(long userId, int limit) {
        var sql = "select * from vocabulary where  userid = ? order by random() limit ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            List<String> translations = Arrays.asList((String[]) rs.getArray("translations").getArray());
            List<String> synonyms = Arrays.asList((String[]) rs.getArray("synonyms").getArray());
            List<String> examples = Arrays.asList((String[]) rs.getArray("examples").getArray());
           return Vocabulary.builder()
                    .id(rs.getLong("id"))
                    .word(rs.getString("word"))
                    .translations(translations)
                    .synonyms(synonyms)
                    .examples(examples)
                    .userid(rs.getLong("userid"))
                    .created_date(rs.getDate("created_date").toLocalDate())
                    .build();
        },userId,limit);
    }
    public List<Vocabulary> findByUserID(long userId) {
        var sql = "select * from vocabulary where  userid = ? order by created_date desc";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            List<String> translations = Arrays.asList((String[]) rs.getArray("translations").getArray());
            List<String> synonyms = Arrays.asList((String[]) rs.getArray("synonyms").getArray());
            List<String> examples = Arrays.asList((String[]) rs.getArray("examples").getArray());
           return Vocabulary.builder()
                    .id(rs.getLong("id"))
                    .word(rs.getString("word"))
                    .translations(translations)
                    .synonyms(synonyms)
                    .examples(examples)
                    .userid(rs.getLong("userid"))
                    .created_date(rs.getDate("created_date").toLocalDate())
                    .build();
        },userId);
    }
}

