# Shows classical approaches to concurrency

Most of the commands here should be executed within the `workbench` container
created by the `docker-compose.yaml` at the root of the project.

## Counters

```bash
watch -n 0.1 curl -s -XGET http://classic:7070/counter/v1

# with ApacheBench
ab -c 1000 -n 100000 -m POST http://classic:7070/counter/v1

# with Siege
siege -q -c 1000 -r 100 'http://classic:7070/counter/v1 POST'
```

## Java Memory Model

**NOTE**: this example does not work on JDK 17 and needs to be re-worked 

```bash
curl -XPOST http://classic:7070/jmm/v1
ab -c 1000 -n 1000000 -m POST http://classic:7070/jmm/v1
```

## Wait For Result

```bash
curl -XGET http://classic:7070/wait/v1

ab -c 1000 -n 100000 -m GET http://classic:7070/wait/v1
```

```bash
jstack "$(ps -o pid,args -C java | grep app.jar | awk '{ print $1 }')" | view -
```

## Combine Results

```bash
curl -XGET http://classic:7070/combine/v1
ab -c 10 -n 100 -m GET http://classic:7070/combine/v1
ab -c 100 -n 100000 -m GET http://classic:7070/combine/v1
```