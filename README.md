[![Build Status](https://travis-ci.org/khaosans/simple-service.svg?branch=master)](https://travis-ci.org/khaosans/simple-service)

******simple-service******
To run the program do the following:

```mvn clean compile```

```mvn clean test```

```mvn exec:java```

Usages:

```curl -i --data "url=http://www.google.com" http://localhost:8080/theapp/cache```

Will return a shaHex value:

```738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1```

With this value you can run a get:

```curl -X GET http://localhost:8080/theapp/cache/738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1```

This return a string version of the response headers:

```HTTP/1.1 200 OK
   Content-Type: text/plain
   Date: Wed, 17 May 2017 01:00:55 GMT
   Content-Length: 40
   
   738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1HTTP/1.1 200 OK
   Last-Modified: Wed, 17 May 2017 01:00:55 GMT
   Cache-Control: private, no-transform, max-age=10
   Content-Type: text/plain
   Date: Wed, 17 May 2017 01:00:55 GMT
   Content-Length: 614
   
   Date: Wed, 17 May 2017 01:00:54 GMTExpires: -1Cache-Control: private, max-age=0Content-Type: text/html; charset=ISO-8859-1P3P: CP="This is not a P3P policy! See https://www.google.com/support/accounts/answer/151657?hl=en for more info."Server: gwsX-XSS-Protection: 1; mode=blockX-Frame-Options: SAMEORIGINSet-Cookie: NID=103=J8nlUUThKtWfltqcuTqTZZOO4o2qc9PWAc7AgJr5ga7hU5qneUBd2PRQxMbpZ4c_GdXE3tKJfcBDXMrQyX8OxOd9O-IbycVPLJd1NvB7Lh_--IpC_iZ
```

For testing:

```curl -i --data "url=http://www.google.com" http://localhost:8080/theapp/cache && curl -i http://localhost:8080/theapp/cache/738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1```