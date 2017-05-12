package com.example;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Root resource (exposed at "myresource" path)
 */
@Singleton
@Path("cache")
public class MyResource {

    private Map<UUID, String> mapOfUrls = new HashMap<>();
    private Date lastModified = new Date();
    private UUID uuid = new UUID(123456789L, 123456789L);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{UUID}")
    public Response getUserHistory(@PathParam("UUID") UUID uuid) {

        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);
        cc.setPrivate(true);

        String url = mapOfUrls.get(uuid);

        return Response.status(200)
                .entity("Your url be : " + url + "\n").lastModified(lastModified).cacheControl(cc)
                .build();

    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{URL}")
    public Response putUrl(@PathParam("URL") String url) {
        String result = "Url has been saved and the id is: " + uuid + "\n";

        mapOfUrls.put(uuid, url);

        return Response.status(201).entity(result).build();
    }
}
