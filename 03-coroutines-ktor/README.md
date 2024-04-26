# Uses Kotlin's coroutines for handling concurrency

## Test API via IntelliJ

To call API running on localhost you can use [coroutines-api.http](coroutines-api.http)

## Counters :)

```bash
watch -n 0.1 curl -s -XGET http://coroutines:9090/counter/v1

ab -c 1000 -n 100000 -m POST http://coroutines:9090/counter/v1
```

## Determine Winner (Coroutines)

```bash
curl -XGET http://coroutines:9090/winner/single
curl -XGET http://coroutines:9090/winner/stream

ab -c 100 -n 100000 -m GET http://coroutines:9090/winner/single
ab -c 1000 -n 100000 -m GET http://coroutines:9090/winner/single
```
