# Code Challenge from N26
Restful API for statistics for persisting transactions in a custom DataStore 
and and provide statistics about them.

### Running

```zsh
./gradlew bootRun
```

### Testing

```zsh
./gradlew clean test
```

##### Endpoints are be available at `http://localhost:8080/`

##### Methods

```http
POST /transactions
Content-Type: application/json
{
    "amount":10.5,
    "timestamp": 1525036985000
}
```

```http
GET /statistics
```

