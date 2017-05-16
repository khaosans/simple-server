[![Build Status](https://travis-ci.org/khaosans/simple-service.svg?branch=master)](https://travis-ci.org/khaosans/simple-service)
******simple-service******
To run the program do the following:

```mvn clean compile```

```mvn clean test```

```mvn exec:java```

Usages:

```curl -X POST http://localhost:8080/theapp/cache/www.google.com```

Will return a shaHex value:

```d8b99f68b208b5453b391cb0c6c3d6a9824f3c3a```

With this value you can run a get:

```curl -X GET http://localhost:8080/theapp/cache/d8b99f68b208b5453b391cb0c6c3d6a9824f3c3a```

This will return a string version of the response :

(Truncated response below)
```!DOCTYPE html>
   <html lang="en" id="facebook" class="no_js">
   <head><meta charset="utf-8" /><meta name="referrer" content="default" id="meta_referrer" /><script>window._cstart=+new Date();</script><script>function envFlush(a){function b(c){for(var d in a)c[d]=a[d];}if(window.requireLazy){window.requireLazy(['Env'],b);}else{window.Env=window.Env||{};b(window.Env);}}envFlush({"ajaxpipe_token":"AXjPY0VM6lR5MDER","timeslice_heartbeat_config":{"pollIntervalMs":3```