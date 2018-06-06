dockerHubAddr="10.31.0.45:5000"

jarRepoPre="http://10.31.0.45:8090/repository/maven-releases/com/sparkchain"
jarPackage="spc-post-message"
jarVersion="1.0"
jarName=${jarPackage}-${jarVersion}

dockerName=${jarPackage}-ms01-8085
dockerVersion=${jarVersion}

MSNAME="${jarPackage}-ms01-8085"
ip="10.31.0.42"
HTTPPORT="8085"

DBCONN1="jdbc:mysql://10.31.0.43:3306/spark_chain_8083"
DBCONN=`echo $DBCONN1 |sed "s/\//\\\\\\\\\//g"`

consulIP="10.31.0.43"
consulPort="8500"

#toPath=/root/${serverFolder}/
toPath=`pwd`
echo "${toPath}"

dockerPath="${dockerHubAddr}/${dockerName}:${dockerVersion}"
startupFileName=startup.sh

echo "###下载jar###"
wget ${jarRepoPre}/$jarPackage/$jarVersion/${jarName}.jar 

echo "####根据模板生成startup.sh###"
tr -d '\r' < Template${startupFileName} > ${startupFileName}
sed  -i  "s/{{JAR_NAME}}/$jarName/g;" ${startupFileName}
cat ${startupFileName}
sleep 15

echo "####新生成dockerfile文件，并替换生成Dockerfile模板###"
cp TemplateDockerfile  Dockerfile
sed  -i  "s/{{JAR_NAME}}/$jarName/g;s/{{SERVICE_NAME}}/$dockerName/g;s/{{SERVICE_VERSION}}/$dockerVersion/g;" Dockerfile
cat Dockerfile
sleep 15;

#https://www.cnblogs.com/xiaoqi/p/6955288.html
echo "####新生成application.properties###"
tr -d '\r' < Templateapplication.properties > application.properties
sed  -i  "s/{{MS_NAME}}/$MSNAME/g;s/{{HTTP_PORT}}/$HTTPPORT/g;s/{{DBCONN}}/$DBCONN/g;s/{{CONSUL_IP}}/$consulIP/g;s/{{REDIS_IP}}/$consulIP/g;s/{{IP}}/$ip/g;" application.properties
cat application.properties
sleep 15;

echo "####构建新docker####  dockerPath:${dockerPath},toPath:${toPath}"
docker build -t ${dockerPath}   $toPath -f Dockerfile

echo "####上传docker####"
docker push ${dockerPath}
sleep 10

echo "####停止和删除本地运行的docker####"
docker stop  ${MSNAME} ;
docker rm ${MSNAME} ;
 #一定要清除原有镜像，不然不会拉
docker rmi  -f ${dockerPath} ;
sleep 10;

echo "####运行docker MSNAME:${MSNAME}, HTTPPORT:${HTTPPORT},TCPPORT:${TCPPORT}... ####"
docker run -d   --name ${MSNAME}  --restart=always  \
   -p ${HTTPPORT}:${HTTPPORT}   ${dockerPath}

sleep 15

echo "####docker运行日志####"
docker logs  -f ${MSNAME}

