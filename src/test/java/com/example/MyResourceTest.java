package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyResourceTest {
    private HttpServer server;

    private WebTarget target;

    private String googlePost;
    private String facebookPost;
    private String yahooPost;

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

        googlePost = target.path("cache/www.google.com").request().post(null, String.class);
        facebookPost = target.path("cache/www.facebook.com").request().post(null, String.class);
        yahooPost = target.path("cache/www.yahoo.com").request().post(null, String.class);
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
        assertTrue(googlePost.equals("d8b99f68b208b5453b391cb0c6c3d6a9824f3c3a"));
    }

    @Test
    public void testFacebookHex() throws Exception {
        assertTrue(facebookPost.equals("24c4068a738f39f37e3d5ed2bbd8a9881633dc68"));
    }

    @Test
    public void testYahooHex() throws Exception {
        assertTrue(yahooPost.equals("a249a2be47a6662de59e7a6be01d57736cff7748"));
    }
}
