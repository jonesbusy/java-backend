# Down composition
docker-compose down

# Build app
mvn clean install -Ddelaye.cloud.backend.db.host=java-backend-db

# Build container
docker-compose build
docker-compose up -d
sleep 10

# Create schema
mvn post-integration-test -PcreateSchema -Ddelaye.cloud.backend.db.host=localhost -Ddelaye.cloud.backend.db.port=33306

# Test API

# Post raw event
#curl -H "Content-Type: application/json" -X POST -d '{"foo":"bar","toto":"tata"}' http://127.0.0.1:48080/api/events

# Retrieve event
#curl -i -X GET -H "Accept: application/json" http://127.0.0.1:48080/api/events

# Display logs
docker-compose logs -f --tail=2000
