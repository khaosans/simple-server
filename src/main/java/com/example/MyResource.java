package com.example;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
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
    private Map<String, String> mapOfUrls = new HashMap<>();
    private Date lastModified = null;

    private MySql mySql = new MySql();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{UUID}")
    public Response getUrlFromUUID(@PathParam("UUID") String uuid) {

        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);
        cc.setPrivate(true);

        String url = mapOfUrls.get(uuid);
        String urlFromDb = null;

        try {
            urlFromDb = mySql.get(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.status(200)
                .entity("Your url be : " + url + "\n" + "Your url from the db: " + urlFromDb + "\n").lastModified(lastModified).cacheControl(cc)
                .build();

    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("yolo")
    public Response getYolo() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(100);
        return Response.status(200).entity("Yolo").cacheControl(cc)
                .build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{URL}")
    public Response addUrl(@PathParam("URL") String url) {
        UUID uuid = UUID.randomUUID();
        String result = "Url has been saved and the id is: " + uuid + "\n";
        lastModified = new Date();

        mapOfUrls.put(String.valueOf(uuid), url);

        try {
            mySql.add(String.valueOf(uuid), url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(result).build();
    }
}
