# suupaa-manga-be

## How to run

### Prepare kafka topology
 - `zookeeper-server-start.bat config\zookeeper.properties`
 - `kafka-server-start.bat config\server.properties`
 - `kafka-topics.bat --create --topic manga-rate --replication-factor 1 --partitions 3 --bootstrap-server localhost:9092`
 - `kafka-topics.bat --create --topic manga-view --replication-factor 1 --partitions 3 --bootstrap-server localhost:9092`

### Prepare database
 - `docker run --name dev-mongo -p 27017:27017 -d mongo:4.4-rc-bionic`
 
### Run application 
 - `skaffold dev -f=skaffold.yaml` for **k8s**
 
 OR
 - Run each app with `-Dspring.profiles.active=dev`


 
## TODO
 - make recommendation service scalable
 - unit tests for kafka streams
 - move config to k8s ConfigMap
