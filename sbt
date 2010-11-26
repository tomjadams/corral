#!/bin/bash
#SBT_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
java ${SBT_OPTS} -Dfile.encoding=UTF-8 -Xms128M -Xmx512M -XX:MaxPermSize=256M -XX:NewSize=64M -XX:NewRatio=3 -jar `dirname $0`/project/sbt-launch-0.7.3.jar @sbt.boot.properties "$@"
