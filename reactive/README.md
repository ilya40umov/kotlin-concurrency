# Shows reactive approaches to concurrency

```bash
./gradlew bootRun
```

## Demo API
```bash
curl -XGET http://localhost:8080/demo/random_user_id
curl -XGET http://localhost:8080/demo/user_name?id=1
curl -XGET http://localhost:8080/demo/user_name?id=2
curl -XGET http://localhost:8080/demo/random_user_ids
```

## Determine Winner (WebFlux)

```bash
curl -XGET localhost:8080/webflux/winner/single
ab -c 100 -n 100000 -m GET localhost:8080/webflux/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/webflux/winner/single

curl -XGET localhost:8080/webflux/winner/stream
```

## Determine Winner (Akka)

```bash
curl -XGET localhost:8080/akka/winner/single
ab -c 100 -n 100000 -m GET localhost:8080/akka/winner/single
ab -c 1000 -n 100000 -m GET localhost:8080/akka/winner/single
```