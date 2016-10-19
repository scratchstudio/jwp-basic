package next.dao;

import core.jdbc.KeyHolder;
import next.dao.jdbc.JdbcTemplate;
import next.dao.jdbc.PreparedStatementCreator;
import next.dao.jdbc.RowMapper;
import next.model.Answer;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Kj Nam
 * @since 2016-10-16
 */
public class AnswerDao {
    public Answer insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
            pstmt.setLong(4, answer.getQuestionId());
            return pstmt;
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public Answer findById(long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                rs.getTimestamp("createdDate"), rs.getLong("questionId"));

        return jdbcTemplate.queryForObject(sql, rm, answerId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc";

        RowMapper<Answer> rm = rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                rs.getTimestamp("createdDate"), questionId);

        return jdbcTemplate.query(sql, rm, questionId);
    }

    public void delete(Long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
    }
}
