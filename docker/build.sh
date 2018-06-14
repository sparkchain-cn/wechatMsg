#!/usr/bin/env bash
#第一次运行
#ssh-keygen
#ssh-copy-id -i ~/.ssh/id_rsa.pub root@10.1.12.200
#ssh-copy-id -i /c/Users/think/.ssh/id_rsa  root@10.1.12.200

#ssh-copy-id -i /c/Users/think/.ssh/id_rsa  root@10.1.12.200

adminIp=110.1.12.200
#jarName=spc-chain-basev3-boot-1.0
#在服务器建立这个文件夹，不能使用jarName,如果同一个jar，多次（多端口）部署就会有问题
serverFolder=spc-post-message-ms01-8085-test
folder=docker

startupFileName=startup.sh
execFileName=docker.sh


deployDir=`pwd`
echo "deployDir:$deployDir"

#生成最新的JAR
cd ../
rootDir=`pwd`
echo "rootDir:$rootDir"

cd ../ 
#在这里编译有问题，说明不能采用git Base编译，要预先采用原生cmd的编译
#mvn clean install -DskipTests

cd ${rootDir}
#mvn clean deploy -DskipTests

sleep 10

jarPath="${rootDir}/target/";
dockerfilePath="${rootDir}/${folder}/";

#到服务器上创建目录
toPath=/home/sparkchain/${serverFolder}/
#ssh root@$adminIp "rm -rf  $toPath ; mkdir -p $toPath"
ssh sparkchain@$adminIp "rm -rf  $toPath ; mkdir -p $toPath"

#复制模板和执行文件
scp ${dockerfilePath}$execFileName  sparkchain@$adminIp:${toPath}${execFileName}
scp  ${dockerfilePath}$startupFileName  sparkchain@$adminIp:${toPath}Template${startupFileName}
scp  ${dockerfilePath}Dockerfile  sparkchain@$adminIp:${toPath}TemplateDockerfile
scp  ${dockerfilePath}application.properties  sparkchain@$adminIp:${toPath}Templateapplication.properties

#scp ${jarPath}${jarName}.jar  root@$adminIp:${toPath}${jarName}.jar

#执行命令文件
echo  $toPath

sleep 10
ssh sparkchain@$adminIp " cd ${toPath}; chmod 777 ${execFileName} ; ls ${toPath};  tr -d '\r' < ${toPath}${execFileName} > ${toPath}1${execFileName} ;bash ${toPath}1${execFileName}   "
