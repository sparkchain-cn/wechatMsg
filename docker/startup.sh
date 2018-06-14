  export POD_IP=`/sbin/ifconfig -a | grep inet | grep -v 127.0.0.1 | grep -v inet6 | awk '{print \$2}' | tr -d "addr" `
  echo " ############POD_IP     $POD_IP"
  echo  "############ALL_CONF: ${ALL_CONF}"

  cat /application.properties
   java -jar -Dspring.config.location=/application.properties  /{{JAR_NAME}}.jar
   
  #java -jar -Xms256m  -Xmx512m  /{{JAR_NAME}}.jar  ${ALL_CONF}
  #java -jar {{JAR_NAME}}.jar  ${ALL_CONF}