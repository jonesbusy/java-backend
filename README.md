# Java backend

## Requirements

| Dependency | Version | Description |
| ---------- | ------- | ----------- |
| Java | >=1.8| Java |
| Maven | 3.5.0| Maven |
| Docker | >=17.06.2-ce | Docker engine |
| Docker compose | >=1.16.1 | Container Composition |

## Build an run the project

```
./build.sh
```


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
