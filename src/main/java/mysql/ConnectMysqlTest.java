package mysql;

import java.sql.*;

public class ConnectMysqlTest {
    public static void main(String[] args) throws SQLException {


        String url = "jdbc:mysql://localhost:3306/test";
        String user= "root";
        String passwd= "123456";
        Connection conn= DriverManager.getConnection(url,user,passwd);


        Statement state=conn.createStatement();

        String s = "select * from demo";
        ResultSet rs = state.executeQuery(s);

        while(rs.next())
        {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
        }
        rs.close();
    }
}
