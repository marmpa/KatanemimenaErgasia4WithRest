/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariostom.ergasia4katanemimena.RestMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/Delete")//Δηλώνει το που θα βρίσκεται αυτό το resource
public class DeleteResource 
{

    @Context
    private UriInfo context;
    private Statement stat;
    private Connection conn;

    
    public DeleteResource() {}

    
    @DELETE//annotation το οποίο λέει στο rest οτι πρόκειτε για τύπου delete
    @Produces("application/json")//παράγει json δεδομένα
    public Response Delete(@HeaderParam("mes") String jsonData) //οτι το jsonData το παίρνει απο το header που δίνεται στο κάλεσμα
    {
        try 
        {
            EstablishDbConnection();//σύνδεση με τη βάση
            ObjectMapper mapper = new ObjectMapper();//mapper το οποίο χρησιμοποιήτε παρακάτω για μετατροπή απο json σε RestMessage
            RestMessage rest=null;
            try
            {
                rest = mapper.readValue(jsonData, RestMessage.class);//δοκιμάζει να κάνει την μετατροπή σε RestMessage
                return deleteFriend(rest);//αν πετύχει καλεί την DeleteFriend
            }
            catch (IOException ex)
            {
                return deletePost(jsonData);//αν υπάρχει ioexception δοκιμάζει να καλέσει την deletePost
            }
            catch(Exception ex)
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(DeleteResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(DeleteResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    public Response deleteFriend(RestMessage rest)
    {
        try 
        {
            //δημηουργεί το string το οποίο θα τρέξει στη βάση
            String message = "Delete from friends where friend_send='" + rest.getName() + "' and friends_username='" + rest.getUser_received()+"'";
            stat.executeUpdate(message);//τρέχει στη βάση την εντολή
            System.out.println("Query executed : " + message);
            return Response.status(Response.Status.NO_CONTENT).build();//επιστρέφει μία απάντηση
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response deletePost(String id)
    {
        try //παρομοίως αλλα διαγράφει κάτι
        {
            String message = "Delete from posts where id='" + id + "'";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
            return Response.status(Response.Status.NO_CONTENT).build();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public void EstablishDbConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");//κάνει την σύνδεση στη βάση
        conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
        stat = conn.createStatement();
    }
    
    public void DestroyDbConnection() throws SQLException
    {//κλήνει την σύνδεσει
        stat.close();
        conn.close();
    }

}
