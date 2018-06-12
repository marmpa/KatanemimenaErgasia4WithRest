/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paths;

import com.mariostom.ergasia4katanemimena.JsonDateSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mariostom.ergasia4katanemimena.JsonDateDeserializer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.mariostom.ergasia4katanemimena.RestMessage;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;


/**
 * REST Web Service
 *
 * @author Marios
 */
@Path("/Get")
public class GetResource 
{
    
    @Context
    private UriInfo context;
    Statement stat,statNew;
    Connection conn;

    /**
     * Creates a new instance of Conf1
     */
    public GetResource() {
    }

    /**
     * Retrieves representation of an instance of com.test.GetResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson5(@QueryParam("name") @DefaultValue("") String name,@QueryParam("option")String option)
    {
        try 
        {
            EstablishDbConnection();
            
            switch(option)
            {
                case "showFriends":
                    return showFriends(name);
                case "showPost":
                    return showPost(name);
                case "topPosts":
                    return topPosts(name);
                default:
                    break;
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(this).build();
    }
    
    public Response showFriends(String name)
    {
        ResultSet records = null;
        ResultSet friends = null;
        String str = "";
        String fr_us, na, su, us,ge , de, ci, co;
        java.util.Date bi; 
        ArrayList<RestMessage> AR= new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try
        {
            records = stat.executeQuery("Select friends_username from friends WHERE friend_send='" + name + "'");
            while (records.next()) 
            {
                fr_us = records.getString("friends_username");
                System.out.println(fr_us+" friend username");
                friends = statNew.executeQuery("Select * from users WHERE username='" + fr_us + "'");
                
                if(friends.next())
                {
                    na = friends.getString("name");
                    su = friends.getString("surname");
                    us = friends.getString("username");
                    bi = dateFormat.parse(friends.getString("birthday"));
                    ge = friends.getString("gender");
                    de = friends.getString("description");
                    co = friends.getString("country");
                    ci = friends.getString("city");
                    AR.add(new RestMessage(na,su,us,bi,ge,de,co,ci));
                }
            }
            DestroyDbConnection();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(java.util.Date.class, new JsonDateSerializer());
            mapper.registerModule(module);
            String result = mapper.writeValueAsString(AR);
            //System.out.println(result);
            return Response.status(Response.Status.OK).entity(result).build();
            
        }
        catch (SQLException ex) {
            System.out.println("Error in showfriend selecting");
            Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
         catch (JsonProcessingException ex) {
                //Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
            Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response showPost(String name)
    {
        ResultSet records = null;
        ArrayList<RestMessage> AR= new ArrayList<>();
        try {
            records = stat.executeQuery("SELECT * from posts WHERE user_posted='" + name + "'");

        } catch (SQLException ex) {
            //Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String i, d, n, m, r;
        String str = "";
        try {
            while (records.next()) {
                i = records.getString("id");
                d = records.getString("date");
                n = records.getString("user_posted");
                r = records.getString("user_received");
                m = records.getString("message");
                str = str + i + " " + d + " " + n + " " + m + "\n";
            }
            DestroyDbConnection();
            return Response.status(Response.Status.OK).entity("marios").build();
        } 
        catch (SQLException ex) {
            //Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response topPosts(String name)
    {
        ResultSet records = null;
        String i, p, m, r;
        java.util.Date d;
        String str = "";
        ArrayList<RestMessage> AR= new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            records = stat.executeQuery("SELECT * from posts WHERE user_posted='" + name + "' LIMIT 10");
            try {
                RestMessage rest;
                while (records.next()) {
                    
                    i = records.getString("id");
                    d = dateFormat.parse(records.getString("date"));
                    p = records.getString("user_posted");
                    r = records.getString("user_received");
                    m = records.getString("message");
                    
                    rest = new RestMessage(i, p, r, m);
                    rest.setDate(d);
                    
                    AR.add(rest);
                }
                DestroyDbConnection();
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(java.util.Date.class, new JsonDateSerializer());
                mapper.registerModule(module);
                String result = mapper.writeValueAsString(AR);
                return Response.status(Response.Status.OK).entity(result).build();
            } catch (SQLException ex) {
               // Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(GetResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
           // Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public void EstablishDbConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
        stat = conn.createStatement();
        statNew = conn.createStatement();
    }
    
    public void DestroyDbConnection() throws SQLException
    {
        stat.close();
        statNew.close();
        conn.close();
    }
    

}