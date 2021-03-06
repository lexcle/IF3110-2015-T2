package org.stackexchange.webservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.stackexchange.Question;
import org.stackexchange.webservice.core.MySQLDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vincentsthe
 */
public class QuestionVoteDao extends MySQLDao {
    
    public boolean existsByQuestionIdUserId(long questionId, long userId) {
        String queryString = "SELECT * FROM `question_vote` WHERE question_id=" + questionId + " AND user_id=" + userId;
        Statement statement;
        boolean exist = false;

        try {
            getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queryString);
            if (rs.next()) {
                exist = true;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }
    
    public int getVoteCountByQuestionIdUserId(long questionId, long userId) {
        String queryString = "SELECT * FROM `question_vote` WHERE question_id=" + questionId + " AND user_id=" + userId;
        Statement statement;
        int vote = 0;

        try {
            getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queryString);
            if (rs.next()) {
                vote = rs.getInt("vote");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vote;
    }
    
    public void insert(long questionId, long userId, long vote) {
        String query = "INSERT INTO `question_vote` (`question_id`, `user_id`, `vote`) VALUES (" + questionId + ", '" + userId + "', '" + vote + "')";
        System.out.println(query);
        Statement statement;

        try {
            getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();
            int insertedId = 0;
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }

            rs.close();
            statement.close();
            closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    public void update(long questionId, long userId, long vote) {
        String query = "UPDATE `question_vote` SET `vote`=" + vote + " WHERE question_id=" + questionId + " AND user_id=" + userId;
        Statement statement;
        
        try {
            getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            statement.close();
            closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
