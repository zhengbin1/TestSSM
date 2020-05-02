package com.example.testssm.service;

import com.example.testssm.dao.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public LoginUser getUserByName(String username){

        try{
            LoginUser loginUser = null;
            loginUser = jdbcTemplate.queryForObject("select * from users where username = ?", new Object[]{username}, new RowMapper<LoginUser>() {
                @Override
                public LoginUser mapRow(ResultSet resultSet, int i) throws SQLException {
                    long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    LoginUser loginUser = new LoginUser(id, username, password, role);

                    return loginUser;
                }
            });

            return loginUser;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public int insertUserBasic(String username, String password, String role){
        try{
            int ret = jdbcTemplate.update("insert into users values (NULL, ?, ?, ?);", username, password, role);
            return ret;
        }  catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
    }
}
