# Uses Kotlin's coroutines for handling concurrency

```bash
./gradlew run
```

## Determine Winner (Coroutines)

```bash
curl -XGET localhost:9090/winner/single
ab -c 100 -n 100000 -m GET localhost:9090/winner/single
ab -c 1000 -n 100000 -m GET localhost:9090/winner/single
```

## Determine Winner (Channels)

```bash
curl -XGET localhost:9090/winner/stream
```

## Counters :)

```bash
curl -XGET localhost:9090/counter/v1
ab -c 1000 -n 100000 -m POST localhost:9090/counter/v1
curl -XGET localhost:9090/counter/v1
```