package com.example;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Root resource (exposed at "myresource" path)
 */
@Singleton
@Path("cache")
public class MyResource {

    // curl -i --data "url=http://www.." http://localhost:8080/theapp/cache

    private Date lastModified = new Date();

    private LoadingCache<String, HttpResponse> responseCacheMap = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<String, HttpResponse>() {
                @Override
                public HttpResponse load(String key) throws Exception {
                    return getFromDatabase(key);
                }
            });

    private HttpResponse getFromDatabase(String empId) {
        //TODO implement a database option
        return null;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{shaHex}")
    public Response getFromShaHex(@PathParam("shaHex") String shaHex) throws Exception {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);
        cc.setPrivate(true);

        HttpResponse httpResponse = responseCacheMap.get(shaHex);

        if (httpResponse == null) {
            return Response.status(404).entity("Item does not exist").build();
        }

        String responseAsString = EntityUtils.toString(httpResponse.getEntity());

        return Response.status(200)
                .entity(responseAsString).lastModified(lastModified).cacheControl(cc)
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("yolo")
    public Response getId() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(100);
        return Response.status(200).entity("yolo").cacheControl(cc)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postUrl(@FormParam("url") String url) throws URISyntaxException, IOException, HttpException, NoSuchAlgorithmException {
        HttpResponse response;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        try {
            response = client.execute(request);
        } catch (UnknownHostException e) {
            return Response.status(404).entity("Unknown Host").build();
        }

        String shaHexString = shaHex(url);
        responseCacheMap.put(shaHexString, response);

        return Response.status(200).entity(shaHexString).build();
    }

    private String shaHex(String stringToEncrypt) throws NoSuchAlgorithmException {
        return DigestUtils.shaHex(stringToEncrypt);
    }
}