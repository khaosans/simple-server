****simple-service****

This simple service is an api that allows for the storage of a string which could be a url.  This url is cached in the app once it's stored and is also saved to the persistent databse if one should exist.  In addition to the local cache, the get is cache on the browser with a max-age of 10 seconds.  

``curl -X POST http://localhost:8080/theapp/cache/<url_here>``

It will spit out a uuid which you can then use to retrieve that value:

```
curl -X GET http://localhost:8080/theapp/cache/0e1fcb39-fa02-43e6-b8c2-79425405d1db
   Your url be : second
   Your url from the db: second
```

Database Required--Set up the database (MySql):

Create a database named ```url``` with the following table:
```$xslt
 CREATE TABLE `url` (
          `k` varchar(200) DEFAULT NULL,
          `v` varchar(200) DEFAULT NULL
        ) 
```




