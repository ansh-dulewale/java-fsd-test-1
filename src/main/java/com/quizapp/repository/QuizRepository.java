package com.quizapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.quizapp.model.QuizQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuizRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<QuizQuestion> rowMapper = new RowMapper<QuizQuestion>() {
        @Override
        public QuizQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuizQuestion q = new QuizQuestion();
            q.setId(rs.getInt("id"));
            q.setQuestion(rs.getString("question"));
            q.setOptionA(rs.getString("option_a"));
            q.setOptionB(rs.getString("option_b"));
            q.setOptionC(rs.getString("option_c"));
            q.setOptionD(rs.getString("option_d"));
            q.setCorrectOption(rs.getString("correct_option"));
            return q;
        }
    };

    public List<QuizQuestion> getAllQuestion(){
        String sql = "Select * from quiz_question";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public QuizQuestion getQuestionById(int id){
        String sql = "Select * from quiz_question where id = ?";
        List<QuizQuestion> list = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        return list.isEmpty()? null : list.get(0);
    }

    public void addQuestion(QuizQuestion question){
        String sql = "Insert into quiz_question (question, option_a, option_b, option_c, option_d, correct_option) values(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                question.getQuestion(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectOption()
        );
    }

    public void saveResult(String username, int score, int total){
        String sql = "Insert into quiz_result (username, score, total) values(?, ?, ?)";
        jdbcTemplate.update(sql, username, score, total);
    }
}
