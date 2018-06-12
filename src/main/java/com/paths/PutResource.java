/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mariostom.ergasia4katanemimena.JsonDateDeserializer;
import com.mariostom.ergasia4katanemimena.RestMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Marios
 */
@Path("/Put")
public class PutResource {

    @Context
    private UriInfo context;
    private Statement stat;
    private Connection conn;
    /**
     * Creates a new instance of PutResource
     */
    public PutResource() {
    }

    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(String jsonData,@HeaderParam("friends_name") @DefaultValue("") String friends_name, @HeaderParam("name") @DefaultValue("") String name) 
    {
        try 
        {
            EstablishDbConnection();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Date.class, new JsonDateDeserializer());
            mapper.registerModule(module);
            
            RestMessage rest = mapper.readValue(jsonData, RestMessage.class);
            
            return updateProfile(name ,friends_name, rest);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(PutResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) 
        {
            Logger.getLogger(PutResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) 
        {
            //Logger.getLogger(PutResource.class.getName()).log(Level.SEVERE, null, ex);
            return updatePost(name, jsonData);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response updateProfile(String name, String friends_name, RestMessage msg)
    {
        try {
            String message = "UPDATE users SET name='" + msg.getName() + "', surname='" + msg.getSurname() + "', username='" + msg.getUsername() + "', birthday='" + msg.getDate() + "', gender='" + msg.getGender() + "', description='" + msg.getDescription() + "', country='" + msg.getCountry() + "', city='" + msg.getCity() + "' WHERE name='" + friends_name + "'";
            String message2 = "UPDATE friends SET friends_username='" + msg.getUsername() + "' WHERE friend_send='" + name + "'";
            stat.executeUpdate(message); 
            stat.executeUpdate(message2); 
            System.out.println("Query executed : " + message);
            System.out.println("Query executed : " + message2);
            DestroyDbConnection();
            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            System.out.println("Exception");
            Logger.getLogger(PutResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    public Response updatePost(String id, String post)
    {
        try {
            Date date = new Date();
            String message = "UPDATE posts SET date='" + date + "', message='" + post + "' WHERE id='" + id + "'";
            stat.executeUpdate(message);
            DestroyDbConnection();
            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            //Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
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
