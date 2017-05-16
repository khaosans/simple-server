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

This will return a string version of the response which represents a get from the local cache and checking that response object to verify that it is valid:

```200```