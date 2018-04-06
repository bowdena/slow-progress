The basis of this idea comes from two sites:
https://soucianceeqdamrashti.wordpress.com/2015/12/04/running-karafhawtiocamel-inside-docker/
https://www.theguild.nl/dockerizing-a-custom-karaf-distribution-in-5-minutes/

I would like to get a dockerised Karaf build with built-in hawtio install.

In order to use this, build the docker with 
docker build . -t hawtiokaraf

And run with
docker run -p 8181:8181 -d hawtiokaraf

If you don't care about the ports, you can run with:
docker run -P -d hawtiokaraf.  

docker ps will then list all the exposed ports

##Troubleshooting

To run in interactive mode: docker run -p 8181:8181 -it hawtiokaraf
To get the IP address (windows): docker inspect a9bb99c081d0  | Select-String 'IPAddress'
To get into the environment in interactive mode: docker run -it --entrypoint /bin/sh hawtiokaraf

