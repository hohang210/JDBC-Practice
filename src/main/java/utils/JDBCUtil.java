package utils;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JDBCUtil {
    public static Connection connection = null;

    public JDBCUtil() {}

    /**
     * connect to mysql database via jdbc
     */
    public static void getConnection() {
        PropertiesUtil.loadProperties("mysql/jdbc.properties");
        String driver = PropertiesUtil.getPropertyValue("driver");
        String url = PropertiesUtil.getPropertyValue("url");
        String username = PropertiesUtil.getPropertyValue("username");
        String password = PropertiesUtil.getPropertyValue("password");

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * disconnect from mysql database
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * transfer the resultSet to a list
     *
     * @param resultSet a result obtained from executing sql through jdbc
     *
     * @return a list of data from the database
     */
    public static List<Map<String, Object>> transferResultSet(ResultSet resultSet) {
        List<Map<String, Object>> result = new LinkedList<>();

        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> objectMap = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    objectMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }

                result.add(objectMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
