
package com.mariostom.ergasia4katanemimena;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.uri.UriComponent;


public class RestClientMain 
{
    public static void main(String[] args)
    {
        Client client = ClientBuilder.newClient();
        
        WebTarget target_post = client.target("http://localhost:8080/Ergasia4Katanemimena/api/Post");
        WebTarget target_get = client.target("http://localhost:8080/Ergasia4Katanemimena/api/Get");
        WebTarget target_delete = client.target("http://localhost:8080/Ergasia4Katanemimena/api/Delete");
        WebTarget target_put = client.target("http://localhost:8080/Ergasia4Katanemimena/api/Put");
        
        String input = "{\"name\":\"Jack\",\"id\":\"Fk\"}";
        
        Response res=null;
        try
        {
            RestMessage rest = new RestMessage("tom", "lia", "showtime", new Date(), "male", "hello everyone", "greece", "athens");
            RestMessage rest2 = new RestMessage("marios", "lia", "legend", new Date(), "male", "hello everyone", "greece", "athens");
            RestMessage rest3 = new RestMessage("jim", "lia", "marmpa", new Date(), "male", "hello everyone", "greece", "athens");
            RestMessage restnew = new RestMessage("greg", "lia", "daddy", new Date(), "male", "hello everyone", "greece", "athens");
            RestMessage rest4 = new RestMessage("tom", "legend");
            RestMessage rest5 = new RestMessage("tom", "marmpa");
            RestMessage rest6 = new RestMessage("marios", "showtime");
            RestMessage restPost1 = new RestMessage("001","tom","tom","geia sou apo tom");
            RestMessage restPost2 = new RestMessage("002","tom","jim","Ta ipiame xthes marakia");
            RestMessage restPost3 = new RestMessage("003","jim","marios","den eimai fake");
            RestMessage restPost4 = new RestMessage("004","tom","marios","den eimai fake");
            //RestMessage rest = new RestMessage("001","client01", "client01", "Hello World!!!!");
            System.out.println(rest.getDate());
            //1528726231409
            Date d =  new Date();
            //1528727167293
            ObjectMapper mapper = new ObjectMapper();
            String headVal = mapper.writeValueAsString(rest4);
            
                //res = target.request(MediaType.APPLICATION_JSON).header("mes", rest).delete();
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","drop").post(Entity.json(rest));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","register").post(Entity.json(rest));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","register").post(Entity.json(rest2));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","register").post(Entity.json(rest3));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","addFriend").post(Entity.json(rest4));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","addFriend").post(Entity.json(rest5));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","addFriend").post(Entity.json(rest6));
                
                res = target_get.queryParam("name", "tom").queryParam("option", "showFriends").request().get();
                res = target_delete.request(MediaType.APPLICATION_JSON).header("mes", headVal).delete();
                res = target_put.request().header("name", "tom").header("friends_name", "jim").put(Entity.json(restnew));
                res = target_get.queryParam("name", "tom").queryParam("option", "showFriends").request().get();
                
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","createPost").post(Entity.json(restPost1));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","createPost").post(Entity.json(restPost2));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","createPost").post(Entity.json(restPost3));
                res = target_post.request(MediaType.APPLICATION_JSON).header("option","createPost").post(Entity.json(restPost4));
                
                
                res = target_delete.request(MediaType.APPLICATION_JSON).header("mes", "001").delete();
                res = target_put.request().header("name","004").put(Entity.json("Ox alakse"));
                res = target_get.queryParam("name", "tom").queryParam("option", "topPosts").request().get();
                
            //res = target.queryParam("name", "tom").queryParam("option", "1").request().get();
            //res = target.request(MediaType.APPLICATION_JSON).post(Entity.json(input));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        if(res.getStatus() != 201)
        {
            System.out.println("Status: "+res.getStatus());
            System.out.println("Something went wrong");
            String result = res.readEntity(String.class);
            System.out.println(result);
        }
        else
        {
            String result = res.readEntity(String.class);
            System.out.println(result);
        }
    }
}
