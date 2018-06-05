/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSA_cw1_ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.sql.DataSource;

/**
 *
 * Name - Charana Mayakaduwa
 * IIT ID - 2016139
 * UOW ID - w1626663
 */
@WebService(serviceName = "Login")
public class Login {

    @Resource(name = "CSA_DB")
    private DataSource CSA_DB;
    private Connection dbConnection;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        initializeDatabase();
        try {
            Statement loginStatement = dbConnection.createStatement();
            ResultSet userList = loginStatement.executeQuery("SELECT * FROM user");

            while (userList.next()) {
//                System.out.println(userList.getString("Nickname"));
                String username = (userList.getString("ID"));
                String UserPassword = (userList.getString("Password"));
                
                if(userName.equals(username) && UserPassword.equals(password)){
                    return true;
                    
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void initializeDatabase() {
        try {
            dbConnection = CSA_DB.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "register")
    public boolean register(@WebParam(name = "name") String name, @WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        initializeDatabase();
        
        try {
            Statement regStatement = dbConnection.createStatement();
            regStatement.execute("INSERT INTO `user` (`ID`, `Password`, `Nickname`) VALUES ('" + userName + "', '" + password + "', '" + name + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
