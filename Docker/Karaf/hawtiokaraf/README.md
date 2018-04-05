The basis of this idea comes from two sites:
https://soucianceeqdamrashti.wordpress.com/2015/12/04/running-karafhawtiocamel-inside-docker/
https://www.theguild.nl/dockerizing-a-custom-karaf-distribution-in-5-minutes/

I would like to get a dockerised Karaf build with built-in hawtio install.

In order to use this, build the docker with 
docker build . -t hawtiokaraf

And run with
docker run -p 8181:8181 -d hawtiokaraf

