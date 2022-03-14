# meshItUp

## Install

```mvn install```


## Run tests

```mvn test```

## Serverless offline

This will fail because of the serverless offline throwing an error:

```serverless offline```

```aws lambda invoke /dev/null --endpoint-url http://localhost:3002 --function-name xibix-dev-meshItUp```
