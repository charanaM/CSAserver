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
import java.util.ArrayList;
import java.util.Date;
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
@WebService(serviceName = "Threads")
public class Threads {

    @Resource(name = "CSA_DB")
    private DataSource CSA_DB;
    
    private Connection dbConnection;


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
    @WebMethod(operationName = "getAllThreads")
    public java.util.ArrayList<java.lang.String> getAllThreads() {
        
        ArrayList<String> string = new ArrayList();
        
        initializeDatabase();
        try {
            Statement getThreadStatement = dbConnection.createStatement();
            ResultSet userList = getThreadStatement.executeQuery("SELECT * FROM thread");

            while (userList.next()) {
                //System.out.println(userList.getString("Title")+"\t"+userList.getDate("LastEdited")+"\t"+userList.getString("CreatedBy"));
                string.add(userList.getString("ThreadID")+" "+ userList.getString("Title")+" "+userList.getDate("LastEdited")+" "+userList.getString("CreatedBy"));
  
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return string;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createThread")
    public String createThread(@WebParam(name = "title") String title, @WebParam(name = "createDate") String createDate, @WebParam(name = "createdBy") String createdBy) {
        
        initializeDatabase();
        
        try {
            Statement createThreadStatement = dbConnection.createStatement();
            createThreadStatement.execute("INSERT INTO `thread` (`ThreadID`, `Title`, `LastEdited`, `CreatedBy`) VALUES (NULL, '" + title + "', '"+ createDate +"', '"+ createdBy +"')");
        } catch (SQLException ex) {
            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "displayChats")
    public ArrayList<java.lang.String> displayChats(@WebParam(name = "threadID") int threadID) {
        ArrayList<String> string = new ArrayList();
        
        initializeDatabase();
        try {
            Statement displayChatStatement = dbConnection.createStatement();
            ResultSet chatList = displayChatStatement.executeQuery("SELECT * FROM message WHERE message.ThreadID = "+ threadID);

            while (chatList.next()) {
                //System.out.println(userList.getString("Title")+"\t"+userList.getDate("LastEdited")+"\t"+userList.getString("CreatedBy"));
                string.add(chatList.getString("ThreadID")+" "+ chatList.getString("Content")+" "+chatList.getDate("CreatedBy")+" "+chatList.getString("CreatedTime"));
                System.out.println(chatList.getString("ThreadID")+" "+ chatList.getString("Content")+" "+chatList.getDate("CreatedBy")+" "+chatList.getString("CreatedTime"));
  
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return string;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "newChatEntry")
    public boolean newChatEntry(@WebParam(name = "message") String message, @WebParam(name = "lastEdited") String lastEdited, @WebParam(name = "addedBy") String addedBy, @WebParam(name = "threadID") int threadID) {
        initializeDatabase();
        
        try {
            Statement newChatStatement = dbConnection.createStatement();
            newChatStatement.execute("INSERT INTO `message` (`Content`, `CreatedBy`, `CreatedTime`, `ThreadID`) VALUES ('" + message + "', '" + addedBy +"', '" + lastEdited + "', '" + threadID + "');");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
