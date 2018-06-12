
package com.paths;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mariostom.ergasia4katanemimena.JsonDateDeserializer;
import com.mariostom.ergasia4katanemimena.RestMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Marios
 */
@Path("/Post")//δείχνει το που βρίσκεται το resource
public class PostResource {

    @Context
    private UriInfo context;
    private Statement stat;
    private Connection conn;
    
    public PostResource() {
    }

    
    @POST//κάνει handle request τύπου post
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(String jsonData,@HeaderParam("option") String option) 
    {
        try 
        {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Date.class, new JsonDateDeserializer());
            mapper.registerModule(module);
            
            RestMessage rest = mapper.readValue(jsonData, RestMessage.class);//δοκιμάζει να μετατρέπει το json σε Date
            
            EstablishDbConnection();//κάνει σύνδεση
            System.out.println(rest);
            
            
            switch(option)
            {//καλέι ανάλογα με την επιλογή
                case "register":
                    return register(rest);
                case "addFriend":
                    return addFriend(rest);
                case "createPost":
                    return createPost(rest);
                case "drop":
                    DropTable();
                default:
                    break;
            }
            
        } catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            //Logger.getLogger(PostResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response register(RestMessage msg)
    {
        System.out.println("Calling register");
        try 
        {//κάνει register τον χρήστη
            String message = "INSERT INTO users (name ,surname ,username ,birthday ,gender ,description ,country , city) VALUES ('" + msg.getName() + "','" + msg.getSurname() + "','" + msg.getUsername() + "','" + msg.getDate() + "','" + msg.getGender() + "','" + msg.getDescription() + "','" + msg.getCountry() + "','" + msg.getCity() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
            ResultSet a = conn.createStatement().executeQuery("Select * from users");
            if(a.next())
            {
                System.out.println(a.getString("name"));
            }
            DestroyDbConnection();//κλήνει την σύνδεση
            String responseMessage = "User created";
            return Response.status(Response.Status.CREATED).entity(responseMessage).build();//επιστρέφει απάντηση
        } 
        catch (SQLException ex) 
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    public Response addFriend(RestMessage msg)
    {
        try {//παρομοίως sql ερώτημα για να προσθέση φίλο
            String message = "INSERT INTO friends (friend_send ,friends_username) VALUES ('" + msg.getName() + "','" + msg.getUser_received()+ "')";
            stat.executeUpdate(message);
            System.out.println("Query executed : " + message);
            
            DestroyDbConnection();//κλήνει την σύνδεση
            String responseMessage = "Friend added";
            return Response.status(Response.Status.CREATED).entity(responseMessage).build();

        } catch (SQLException ex) 
        {
            
            //Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    public Response createPost(RestMessage msg)
    {//κάνει δημιουργεία νέου post
        System.out.println("Calling createPOst");
        try 
        {
            String message = "INSERT INTO posts (id, date, user_posted, user_received, message) VALUES ('" + msg.getID() + "','" + msg.getDate() + "','" + msg.getName() + "','" + msg.getUser_received()+ "','" + msg.getPost() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
            DestroyDbConnection();
            String responseMessage = "Post Created";
            return Response.status(Response.Status.CREATED).entity(responseMessage).build();
        } catch (SQLException ex) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    public void EstablishDbConnection() throws ClassNotFoundException, SQLException
    {//σύνδεση στη βάση
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
        stat = conn.createStatement();
    }
    
    public void DestroyDbConnection() throws SQLException
    {//κλήνει την σύνδεση
        stat.close();
        conn.close();
    }
    
    public void DropTable()
    {
        try {//κάνει drop και δημιουργεία όλα τα tables
            stat.executeUpdate("DROP table if exists users;");
            stat.executeUpdate("DROP table if exists posts;");
            stat.executeUpdate("DROP table if exists friends;");
            stat.executeUpdate("CREATE table users (name varchar(50),surname varchar(50),username varchar(50),birthday date,gender varchar(10) ,description varchar(200),country varchar(30), city varchar(30));");
            stat.executeUpdate("CREATE table posts (id varchar(50),date varchar(50),user_posted varchar(50),user_received varchar(50), message varchar(200));");
            stat.executeUpdate("CREATE table friends (friend_send varchar(50),friends_username varchar(50));");
            DestroyDbConnection();
        } catch (SQLException ex) {
            //Logger.getLogger(PostResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
