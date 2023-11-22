FROM openjdk:17

LABEL maintainer="Zaki<cnzakii@gmail.com>"

#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

#设置编码
ENV LANG C.UTF-8

VOLUME /tmp

WORKDIR /root/

ADD tiedyer-backend-1.0.0.jar /root/tiedyer-backend.jar

RUN bash -c 'touch /root/tiedyer-backend.jar'

ENTRYPOINT ["java","-jar","/root/tiedyer-backend.jar"]

EXPOSE 7576


# 构建命令
# docker build -t tiedyer/backend:1.0 .

# 运行命令
# docker run -d -p 7576:7576  --network=tiedyer  --name=tiedyer-backend  tiedyer/backend:1.0