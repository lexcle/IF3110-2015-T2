package org.stackexchange.identityservice.dao;

import org.stackexchange.identityservice.core.MySQLDao;
import org.stackexchange.identityservice.model.Token;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDao extends MySQLDao {

    public boolean isTokenExist(String token) {
        String query = "SELECT * FROM `token` WHERE token='" + token + "'";
        Statement statement;
        boolean exist = false;

        try {
            getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

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

    public Token getTokenFromToken(String token) {
        String query = "SELECT * FROM `token` WHERE token='" + token + "'";
        Statement statement;

        try {
            getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            boolean exist = false;
            long id = 0;
            long userId = 0;
            while (rs.next()) {
                id = rs.getInt("id");
                userId = rs.getInt("user_id");
                exist = true;
            }

            rs.close();
            statement.close();
            if (exist) {
                return new Token(id, token, userId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Token insertToken(long userId, String token) {
        String query = "INSERT INTO `token` (`user_id`, `token`) VALUES (" + userId + ", '" + token + "')";
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

            Token insertedToken = new Token(insertedId, token, userId);

            rs.close();
            statement.close();

            return insertedToken;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
