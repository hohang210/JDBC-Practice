import dao.UsersDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JDBCUtil;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersDaoTests {
    private static UsersDao usersDao;

    @BeforeEach
    void init() {
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

        usersDao = new UsersDao();
    }

    @AfterEach
    void clean() {
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

    @Test
    @DisplayName("Testing add user")
    public void addUserTest() {
        assertEquals(true, usersDao.addUser("Sam", "123456"), "testing add user");
    }

    @Test
    @DisplayName("Testing find user")
    public void findUserTest() {
        usersDao.addUser("Sam", "123456");

        List<Map<String, Object>> actualUsers = usersDao.getUserByUsername("Sam");

        assertEquals("Sam", actualUsers.get(0).get("username"), "Checking username");
        assertEquals("123456", actualUsers.get(0).get("password"), "Checking password");
    }
}
