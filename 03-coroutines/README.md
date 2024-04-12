# Uses Kotlin's coroutines for handling concurrency

### Calling API

See [coroutines-api.http](coroutines-api.http)

## Counters :)

```bash
ab -c 1000 -n 100000 -m POST localhost:9090/counter/v1
```

## Determine Winner (Coroutines)

```bash
ab -c 100 -n 100000 -m GET localhost:9090/winner/single
ab -c 1000 -n 100000 -m GET localhost:9090/winner/single
```
