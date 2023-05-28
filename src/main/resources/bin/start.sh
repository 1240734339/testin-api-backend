#!/bin/sh

pwd=$(cd `dirname $0`; pwd)
echo $pwd
export DEPLOY_PATH=${pwd%/*}
echo ${DEPLOY_PATH}
export JAVA_OPTIONS="-Xmx3096m -Xms2048m -XX:MaxPermSize=2048m"
echo ${JAVA_OPTIONS}

java -cp ${DEPLOY_PATH}/resources:${DEPLOY_PATH}/lib/*:${DEPLOY_PATH}/lib/Api-Cloud-*.jar:. ${JAVA_OPTIONS} cn.testin.Application &
