# Shows reactive approaches to concurrency

```bash
./gradlew bootRun
```

## Combine Results (WebFlux)

```bash
curl -XGET localhost:8080/combine/single
ab -c 100 -n 100000 -m GET localhost:8080/combine/single

curl -XGET localhost:8080/combine/stream
```