package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyResourceTest {
    private HttpServer server;

    private WebTarget target;


    @Before
    public void setUp() throws Exception {


        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);


        //facebookPost = target.path("cache").request().post(null, String.class);
        //yahooPost = target.path("cache").request().post(null, String.class);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getYolo() throws Exception {
        String response = target.path("cache/yolo").request().get(String.class);
        assertEquals(response, "yolo");
    }

    @Test
    public void testGoogleHex() throws Exception {
        MultivaluedHashMap<String, String> form = new MultivaluedStringMap();
        form.add("url", "http://www.google.com");
        String googleHex = target.path("cache").request().post(Entity.form(form), String.class);
        assertTrue(googleHex.equals("738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1"));
        assertTrue(is200Response(form));

    }

    @Test
    public void testFacebookHex() throws Exception {
        MultivaluedHashMap<String, String> form = new MultivaluedStringMap();
        form.add("url", "http://www.facebook.com");
        String faceBookHex = target.path("cache").request().post(Entity.form(form), String.class);
        assertTrue(faceBookHex.equals("fcdaa82b65917e050dcb45f818af3382f8c0a961"));
        assertTrue(is200Response(form));
    }

    @Test
    public void testYahooHex() throws Exception {
        MultivaluedHashMap<String, String> form = new MultivaluedStringMap();
        form.add("url", "http://www.yahoo.com");
        String yahooHex = target.path("cache").request().post(Entity.form(form), String.class);
        assertTrue(yahooHex.equals("11897a5a38e50c6b72c7588c8ed6a153d866d5c0"));
        assertTrue(is200Response(form));
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidUrl() throws Exception {
        MultivaluedHashMap<String, String> form = new MultivaluedStringMap();
        form.add("url", "ht://www-");
        String invalidUrl = target.path("cache").request().post(Entity.form(form), String.class);
        assertTrue(invalidUrl.equals("Invalid Url"));
    }

    @Test(expected = NotFoundException.class)
    public void testBadShaGet() throws Exception {
        String response = target.path("cache/123").request().get(String.class);
        assertTrue(response.equals("Item does not exist"));
    }

    @Test
    public void testPutAndGet() throws Exception {
        MultivaluedHashMap<String, String> form = new MultivaluedStringMap();
        form.add("url", "http://www.google.com");
        String hex = target.path("cache").request().post(Entity.form(form), String.class);
        assertTrue(hex.equals("738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1"));
        String response = target.path("cache/738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1").request().get(String.class);
        assert (Objects.equals(response, "200"));
    }

    private Boolean is200Response(MultivaluedHashMap<String, String> form) {
        int status = target.path("cache").request().post(Entity.form(form)).getStatus();
        return status == 200;
    }
}
