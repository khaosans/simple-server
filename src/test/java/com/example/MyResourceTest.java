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
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getYolo() throws Exception {
        String response = target.path("cache/yolo").request().get(String.class);
        assertEquals(response, "Yolo");
    }


    @Test
    public void addUrl() throws Exception {
        String response = target.path("cache/URL_TO_ADD").request().post(null, String.class);
        assert (response.startsWith("Url has been saved and the id is:"));
    }

    @Test
    public void getUrlFromUUID() throws Exception {
        String postResponse = target.path("cache/URL_TO_ADD").request().post(null, String.class);
        String uuid = postResponse.split(":")[1].replace("\n", "").replace(" ", "");
        String response = target.path(String.format("cache/%s", uuid)).request().get(String.class);

        assertTrue(response.replace("\n", "").endsWith("URL_TO_ADD"));
    }


}
