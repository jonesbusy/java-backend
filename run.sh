#!/usr/bin/env bash

echo "Starting run script ..."

echo "Current user :" $(whoami || echo 1)
id
echo "Groups :" $(groups)
echo "Home :" $HOME
echo "Hostname :" $(hostname -i)
echo "Broker Host :" ${broker_host}
echo "Java Options :" $JAVA_OPTS

/usr/bin/java $JAVA_OPTS -Dlogback.configurationFile=/opt/package/logback.xml -classpath "*:lib/*" cloud.delaye.backend.Main
