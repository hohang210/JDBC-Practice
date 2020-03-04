package dao;

import utils.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UsersDao {
    /**
     * add a new user to database
     *
     * @param username the username of the new user
     * @param password the password of the new user
     *
     * @return a boolean indicating whether successfully added the new user,
     */
    public boolean addUser(String username, String password) {
        JDBCUtil.getConnection();
        String sql = "insert into users values(?, ?)";

        try {
            PreparedStatement preparedStatement = JDBCUtil.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        JDBCUtil.close();

        return true;
    }

    /**
     * find the given user in database
     *
     * @param username the username of the given user
     *
     * @return the information of the given user.
     */
    public List<Map<String, Object>> getUserByUsername(String username) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Map<String, Object>> users = new LinkedList<>();

        String sql = "select * from users where username = ?";

        try {
            JDBCUtil.getConnection();
            preparedStatement = JDBCUtil.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            users = JDBCUtil.transferResultSet(resultSet);

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }

        return users;
    }
}
