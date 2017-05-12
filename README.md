**simple-service**


This is a simple rest client that allows the storage of a url.  

``curl -X POST http://localhost:8080/theapp/cache/<url_here>``

This will store the url in both the local cache and the persistent database. 

It will spit out a uuid which you can then use to retrieve that value:

```
curl -X GET http://localhost:8080/theapp/cache/0e1fcb39-fa02-43e6-b8c2-79425405d1db
   Your url be : second
   Your url from the db: first
```

Set up

Create a database named ```url``` with the following table:
```$xslt
 CREATE TABLE `url` (
          `k` varchar(200) DEFAULT NULL,
          `v` varchar(200) DEFAULT NULL
        ) 
```

Set the password and user accordingly

