# Java backend

## Requirements

| Dependency | Version | Description |
| ---------- | ------- | ----------- |
| Java | 21| Java Development Kit |
| Maven | 3.6.0+| Maven Build Tool |
| Docker | >=17.06.2-ce | Docker engine |
| Docker compose | >=1.16.1 | Container Composition |

## Build and run the project

### Using Maven

```bash
mvn clean compile
```

### Using Docker

```bash
./build.sh
```

## CI/CD

This project uses GitHub Actions for continuous integration. The workflow automatically builds the project on every push and pull request.


## Test API

### Using HTTPie

```
$ http http://127.0.0.1:48080/api/events
```

###  Using curl

```
$ curl -i -X GET -H "Accept: application/json" http://127.0.0.1:48080/api/events
```

###  Using wget

```
wget -qSO- --header=Accept:application/json "http://127.0.0.1:48080/api/events"

```
