package com.carrotfriend.mariadb;

import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Transactional
public class DatabaseTest {
    @Test
    public void dataTest() {
        String url = "jdbc:mariadb://127.0.0.1:3306/carrotfriends?characterEncoding=UTF-8&passwordCharacterEncoding=utf8&serverTimezone=UTC";
        String username = "carrot";
        String pw = "1234";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(url, username, pw);
            ps = con.prepareStatement("select * from user");

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(ps != null) ps.close();
                if(con != null) con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
