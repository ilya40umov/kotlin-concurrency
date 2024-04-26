# Shows reactive approaches to concurrency

## Test API via IntelliJ

To call API running on localhost you can use [reactive-api.http](reactive-api.http)

## Demo API

```bash
curl -XGET http://reactive:8080/demo/random_user_id

curl -XGET http://reactive:8080/demo/user_name?id=1

curl -XGET http://reactive:8080/demo/random_user_ids
```

## Determine Winner (WebFlux)

```bash
curl -XGET http://reactive:8080/webflux/winner/single
curl -XGET http://reactive:8080/webflux/winner/stream

ab -c 100 -n 100000 -m GET http://reactive:8080/webflux/winner/single
ab -c 1000 -n 100000 -m GET http://reactive:8080/webflux/winner/single
```

## Determine Winner (Akka)

```bash
curl -XGET http://reactive:8080/akka/winner/single

ab -c 100 -n 100000 -m GET http://reactive:8080/akka/winner/single
ab -c 1000 -n 100000 -m GET http://reactive:8080/akka/winner/single
```







