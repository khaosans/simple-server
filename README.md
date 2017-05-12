******simple-service******

This simple service is an api that allows for the storage of a string which could also be a url.  This url is cached in the server once a url is posted like below. There is also a required database for persistence.  In addition to the local cache, the get is cache on the browser with a max-age of 10 seconds. 

Some improvements that could be done:
```
De-couple the database and cache and have them be optional so long as one existed.  

Dockerize for easier deployment.

Create a CI build.
```

In order to post, you would need to run this curl command:

``curl -X POST http://localhost:8080/theapp/cache/<url_here>``

It will spit out a uuid which you can then use to retrieve that value (example below):

```
curl -X GET http://localhost:8080/theapp/cache/0e1fcb39-fa02-43e6-b8c2-79425405d1db
   Your url be : second
   Your url from the db: second
```

For now I'm displaying both gets to sanity check that I'm getting one from the local cache and one form the database.  

Database Required--Set up the database (MySql):

Create a database named ```url``` with the following table:
```$xslt
 CREATE TABLE `url` (
          `k` varchar(200) DEFAULT NULL,
          `v` varchar(200) DEFAULT NULL
        ) 
```




