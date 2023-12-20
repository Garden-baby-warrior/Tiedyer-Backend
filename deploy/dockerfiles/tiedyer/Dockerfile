FROM openjdk:17

LABEL maintainer="zaki<cnzakii@gmail.com>"

# 环境变量
ENV TZ=Asia/Shanghai
ENV LANG C.UTF-8

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

VOLUME /tmp

WORKDIR /root

# 将宿主机中的jar包复制进容器
ENV JAR_FILE=tiedyer-backend-1.0.0.jar
COPY ${JAR_FILE} /root/tiedyer-backend.jar

# 暴露容器的端口
ENV EXPOSE_PORT=7576
EXPOSE ${EXPOSE_PORT}


# JWT密钥
ENV JWT_SECRET="--secret--"
# 微信小程序appId
ENV APP_ID=""
# 微信小程序appSecret
ENV APP_SECRET=""

# MySQL配置
ENV MYSQL_ROOT_PASSWORD="--secret--"
ENV MYSQL_DATABASE="tiedyer"

# Redis配置
ENV REDIS_PASSWORD=""

# RabbitMQ配置
ENV RABBITMQ_DEFAULT_USER=tiedyer
ENV RABBITMQ_DEFAULT_PASS=--secret--

CMD java -jar /root/tiedyer-backend.jar \
    --spring.profiles.active=prod \
    --server.port=${EXPOSE_PORT} \
    --my-token.jwt.secret=${JWT_SECRET} \
    --api.wx.appId=${APP_ID} \
    --api.wx.appSecret=${APP_SECRET} \
    --spring.datasource.password=${MYSQL_ROOT_PASSWORD} \
    --spring.data.redis.password=${REDIS_PASSWORD} \
    --spring.rabbitmq.username=${RABBITMQ_DEFAULT_USER} \
    --spring.rabbitmq.password=${RABBITMQ_DEFAULT_PASS} \
    --spring.datasource.url='jdbc:mysql://mysql:3306/${MYSQL_DATABASE}?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai'

# 构建命令
# docker build -t tiedyer/backend:1.0 .

# 运行命令
# docker run -d -p 7576:7576  --network=tiedyer  --name=tiedyer-backend  -v /home/tiedyer/resource:/root/resource tiedyer/backend:1.0