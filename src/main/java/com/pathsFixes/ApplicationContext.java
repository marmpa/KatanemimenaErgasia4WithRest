package com.pathsFixes;

import com.paths.GetResource;
import com.paths.DeleteResource;
import com.paths.PostResource;
import com.paths.PutResource;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")//δείχνει το path για τα resources και για να κάνει το link στην μορφή "api/(get or put or delete or post)"
public class ApplicationContext extends ResourceConfig
{
    public ApplicationContext()
    {
        packages("com.pathsFixes");//δηλώνει το package
        register(PutResource.class);//κάνει register όλες τις κλάσεις για να μπορεί να τις βρεί το rest api
        register(GetResource.class);
        register(PostResource.class);
        register(DeleteResource.class);
        
    }
}
