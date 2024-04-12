# Shows reactive approaches to concurrency

See [reactive-api.http](reactive-api.http) for API endpoints.

### Load tests

## Determine Winner (WebFlux)

```bash
ab -c 100 -n 100000 -m GET localhost:8080/webflux/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/webflux/winner/single
```

## Determine Winner (Akka)

```bash
ab -c 100 -n 100000 -m GET localhost:8080/akka/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/akka/winner/single
```