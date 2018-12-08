# Shows reactive approaches to concurrency

```bash
./gradlew bootRun
```

## Determine Winner (WebFlux)

```bash
curl -XGET localhost:8080/webflux/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/webflux/winner/single

curl -XGET localhost:8080/webflux/winner/stream
```

## Determine Winner (Akka)

```bash
curl -XGET localhost:8080/akka/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/akka/winner/single

curl -XGET localhost:8080/akka/winner/stream
```