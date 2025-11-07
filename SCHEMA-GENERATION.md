# Database Schema Generation

This application supports automatic database schema generation at startup using JPA/EclipseLink.

## How to Generate Schema

To generate or update the database schema, run the application with the system property `delaye.cloud.backend.schema.generation` set to one of the following values:

### Options

- `none` (default): No schema generation
- `create-tables`: Create new tables (does not drop existing tables)
- `drop-and-create-tables`: Drop existing tables and recreate them (USE WITH CAUTION - will delete all data)

### Example Usage

#### With Maven exec plugin:

```bash
mvn exec:java \
  -Ddelaye.cloud.build.package.skip=true \
  -Ddelaye.cloud.backend.db.host=localhost \
  -Ddelaye.cloud.backend.db.port=33306 \
  -Ddelaye.cloud.backend.schema.generation=drop-and-create-tables
```

#### With Docker Compose:

First, start the database:

```bash
docker compose up -d java-backend-db
```

Then run the schema generation:

```bash
mvn exec:java \
  -Ddelaye.cloud.build.package.skip=true \
  -Ddelaye.cloud.backend.db.host=localhost \
  -Ddelaye.cloud.backend.db.port=33306 \
  -Ddelaye.cloud.backend.schema.generation=drop-and-create-tables
```

## Notes

- Schema generation happens automatically at application startup when enabled
- The application will log "Schema generation enabled: {mode}" and "Schema generation completed" when schema generation is active
- If the database connection parameters differ from defaults, pass them as system properties:
  - `delaye.cloud.backend.db.host` (default: java-backend-db)
  - `delaye.cloud.backend.db.port` (default: 3306)
  - `delaye.cloud.backend.db.name` (default: JAVA-BACKEND)

## Migration from Old createSchema Profile

The old Maven profile `createSchema` using `jpa-schema-maven-plugin` has been deprecated because it does not support persistence.xml version 3.0 (Jakarta Persistence). The new approach uses EclipseLink's built-in DDL generation capabilities at application startup, which is more flexible and up-to-date.
