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

(truncated)

```<!doctype html><html itemscope="" itemtype="http://schema.org/WebPage" lang="en"><head><meta content="Search the world's information, including webpages, images, videos and more. Google has many special features to help you find exactly what you're looking for." name="description"><meta content="noodp" name="robots"><meta content="text/html; charset=UTF-8" http-equiv="Content-Type"><meta content="/images/branding/googleg/1x/googleg_standard_color_128dp.png" itemprop="image"><title>Google</title><script>(function(){window.google={kEI:'mZ4bWf_0M4-yjwPMp4DoCA',kEXPI:'201760,1352553,2501257,3700314,3700347,3700410,3700425,4029815,4031109,4032677,4036527,4039268,4040137,4043492,4045841,4048347,4061945,4064904,4065787,4071842,4072364,4072774,4076095,4076999,4078430,4078762,4079444,4081039,4081164,4083496,4090550,4090553,4091060,4093134,4093313,4094252,4094544,4095910,4096324,4097153,4097922,4097929,4097951,4098096,4098733,4098740,4098752,4100169,4100174,4100689,4101429,4101750,4101896,4102238,4102827,4103470,4103475,4103845,4104204,4105085,4105178,4105317,4105321,4105554,4106459,4106949,4107395,4107555,4107628,4107797,4107989,4108537,4108539,4109094,4109223,4109341,4109439,4109490,4109498,4109528,4109539,4109582,4109625,4109631,4110116,4110361,4110656,4111031,4111422,4111680,4111791,4111793,4112041,4112157,8300531,8503585,8508229,8508931,8509037,8509091,8509373,8510343,10200083,19002115,19002174,19002281,19002294,19002296,19002297,41027341',authuser:0,kscs:'c9c918f0_24'};```


For testing:

```curl -i --data "url=http://www.google.com" http://localhost:8080/theapp/cache && curl -i http://localhost:8080/theapp/cache/738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1```