package com.example;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;

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

    private Cache<String, HttpResponse> responseCacheMap = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(100, TimeUnit.SECONDS)
            .recordStats()
            .build();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{shaHex}")
    public Response getFromShaHex(@PathParam("shaHex") String shaHex) throws Exception {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);
        cc.setPrivate(true);

        HttpResponse httpResponse = responseCacheMap.getIfPresent(shaHex);

        if (httpResponse == null) {
            return Response.status(404).entity("Item does not exist").build();
        }

        return Response.status(200)
                .entity(getHeaders(httpResponse.getAllHeaders())).lastModified(lastModified).cacheControl(cc)
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

        if (isValidUrl(url)) {
            try {
                HttpGet request = new HttpGet(url);
                response = client.execute(request);
            } catch (UnknownHostException e) {
                return Response.status(404).entity("Unknown Host").build();
            } catch (HttpHostConnectException e) {
                return Response.status(404).entity("Connection Refused").build();
            } catch (Exception e) {
                return Response.status(404).entity(e.getStackTrace()).build();
            }

            String shaHexString = shaHex(url);
            responseCacheMap.put(shaHexString, response);

            return Response.status(200).entity(shaHexString).build();
        } else {
            return Response.status(400).entity("Invalid Url").build();
        }
    }

    private Boolean isValidUrl(String url) {
        String[] schemes = {"http", "https"};
        return new UrlValidator(schemes).isValid(url);
    }

    private String shaHex(String stringToEncrypt) throws NoSuchAlgorithmException {
        return DigestUtils.shaHex(stringToEncrypt);
    }

    private String getHeaders(Header[] headers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Header header : headers) {
            stringBuilder.append(header.toString());
        }
        return stringBuilder.toString();
    }
}