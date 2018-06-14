FROM registry.cn-hangzhou.aliyuncs.com/prk/centos7_jdk1.8

WORKDIR  /

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
#ENV LC_ALL en_US.UTF-8
#解决时区的问题
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


ADD startup.sh  /startup.sh
ADD application.properties  /application.properties
#ADD quartz.properties  /quartz.properties

ADD {{JAR_NAME}}.jar  /{{JAR_NAME}}.jar

RUN chmod +x /startup.sh
RUN chmod +x /{{JAR_NAME}}.jar

ENTRYPOINT /startup.sh
