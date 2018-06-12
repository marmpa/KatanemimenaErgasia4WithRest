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

/**
 * REST Web Service
 *
 * @author Marios
 */
@Path("/Delete")
public class DeleteResource 
{

    @Context
    private UriInfo context;
    private Statement stat;
    private Connection conn;

    /**
     * Creates a new instance of DeleteResource
     */
    public DeleteResource() {}

    /**
     * Retrieves representation of an instance of com.paths.DeleteResource
     * @return an instance of java.lang.String
     */
    @DELETE
    @Produces("application/json")
    public Response Delete(@HeaderParam("mes") String jsonData) 
    {
        try 
        {
            EstablishDbConnection();
            ObjectMapper mapper = new ObjectMapper();
            RestMessage rest=null;
            try
            {
                rest = mapper.readValue(jsonData, RestMessage.class);
                return deleteFriend(rest);
            }
            catch (IOException ex)
            {
                return deletePost(jsonData);
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
        try {
            String message = "Delete from friends where friend_send='" + rest.getName() + "' and friends_username='" + rest.getUser_received()+"'";
            stat.executeUpdate(message);
            System.out.println("Query executed : " + message);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response deletePost(String id)
    {
        try 
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
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
        stat = conn.createStatement();
    }
    
    public void DestroyDbConnection() throws SQLException
    {
        stat.close();
        conn.close();
    }

}
