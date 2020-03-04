import dao.UsersDao;
import utils.JDBCUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String args[]) {
        // create a users table including username and password
        JDBCUtil.getConnection();
        String createTable = "create table users(username varchar(20) primary key, password varchar(20))";

        try {
            Statement statement = JDBCUtil.connection.createStatement();
            statement.executeUpdate(createTable);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JDBCUtil.close();

        UsersDao usersDao = new UsersDao();
        String username = "abc";
        String password = "123456";

        // add a user to the table
        usersDao.addUser(username, password);

        // search the user called abc
        System.out.println(usersDao.getUserByUsername(username));

        // delete the users table
        JDBCUtil.getConnection();
        String deleteTable = "drop table if exists `users`";

        try {
            Statement statement = JDBCUtil.connection.createStatement();
            statement.execute(deleteTable);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JDBCUtil.close();
    }
}
