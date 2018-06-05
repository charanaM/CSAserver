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
@WebService(serviceName = "Chats")
public class Chats {

    @Resource(name = "CSA_DB")
    private DataSource CSA_DB;
    
    

    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "showChats")
    public ArrayList<java.lang.String> showChats(@WebParam(name = "threadID") int threadID) {
        ArrayList<String> string = new ArrayList();
        
        Connection dbConnection;
        try {
            dbConnection = CSA_DB.getConnection();
            
            Statement displayChatStatement = dbConnection.createStatement();
            ResultSet chatList = displayChatStatement.executeQuery("SELECT * FROM message WHERE message.ThreadID = "+ threadID);

            while (chatList.next()) {
                //System.out.println(userList.getString("Title")+"\t"+userList.getDate("LastEdited")+"\t"+userList.getString("CreatedBy"));
                string.add(chatList.getString("ThreadID")+" "+ chatList.getString("Content")+" "+chatList.getString("CreatedBy")+" "+chatList.getString("CreatedTime"));
                System.out.println(chatList.getString("ThreadID")+" "+ chatList.getString("Content")+" "+chatList.getString("CreatedBy")+" "+chatList.getString("CreatedTime"));
  
            }
        } catch (SQLException ex) {
            Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return string;
    }
}
