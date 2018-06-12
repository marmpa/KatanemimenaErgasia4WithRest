package com.pathsFixes;

import com.paths.GetResource;
import com.paths.DeleteResource;
import com.paths.PostResource;
import com.paths.PutResource;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class ApplicationContext extends ResourceConfig
{
    public ApplicationContext()
    {
        packages("com.pathsFixes");
        register(PutResource.class);
        register(GetResource.class);
        register(PostResource.class);
        register(DeleteResource.class);
        
    }
}
