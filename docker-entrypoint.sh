#!/bin/bash
set -e

echo "Starting docker entrypoint..."

echo "Current user :" $(whoami || echo 1)
echo "Groups :" $(groups)
echo "Home :" $HOME
echo "Java Options :" $JAVA_OPTS
echo "Java Home :" $JAVA_HOME
java -version

echo "Executing command : $@"

exec "$@"
