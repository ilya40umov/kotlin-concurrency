# Shows classical approaches to concurrency

```bash
./gradlew bootRun
```

## Counters

```bash
curl -XGET localhost:8080/counter/v1
ab -c 1000 -n 100000 -m POST localhost:8080/counter/v1
curl -XGET localhost:8080/counter/v1
```

## Java Memory Model

```bash
curl -XPOST localhost:8080/jmm/v1
ab -c 1000 -n 100000 -m POST localhost:8080/jmm/v1
```

## Wait For Result

```bash
curl -XGET localhost:8080/wait/v1
ab -c 1000 -n 100000 -m GET localhost:8080/wait/v1
```

## Combine Results

```bash
curl -XGET localhost:8080/combine/v1
ab -c 10 -n 100 -m GET localhost:8080/combine/v1
ab -c 1000 -n 1000 -m GET localhost:8080/combine/v1

```

