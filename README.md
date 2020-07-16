# suupaa-manga-be

## How to run
 0. install docker+k8s
 0. run `docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=<pass> -d postgres`
 0. run `skaffold dev -f=skaffold.yaml`
 
 
 
 
 ## TODO
 - move config to k8s ConfigMap
 - 