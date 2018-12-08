# Shows classical approaches to concurrency

```bash
./gradlew bootRun
```

## Counters

```bash
curl -XGET localhost:7070/counter/v1
ab -c 1000 -n 100000 -m POST localhost:7070/counter/v1
curl -XGET localhost:7070/counter/v1
```

## Java Memory Model

```bash
curl -XPOST localhost:7070/jmm/v1
ab -c 1000 -n 100000 -m POST localhost:7070/jmm/v1
```

## Wait For Result

```bash
curl -XGET localhost:7070/wait/v1
ab -c 1000 -n 100000 -m GET localhost:7070/wait/v1
```

## Combine Results

```bash
curl -XGET localhost:7070/combine/v1
ab -c 10 -n 100 -m GET localhost:7070/combine/v1
ab -c 100 -n 100000 -m GET localhost:7070/combine/v1

```