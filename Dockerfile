# 使用基础镜像 OpenJDK 17
FROM openjdk:17

# 设置镜像维护者信息
LABEL maintainer="Zaki<cnzakii@gmail.com>"

# 定义时区参数
ENV TZ=Asia/Shanghai

# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

# 设置编码
ENV LANG C.UTF-8

# 创建一个卷用于临时存储数据
VOLUME /tmp

# 指定工作目录为/root
WORKDIR /root

# 复制应用程序到容器
ADD tiedyer-backend-1.0.0.jar /root/tiedyer-backend.jar

# 使用 Java 运行应用程序，设置 Spring 框架的配置为生产环境
ENTRYPOINT ["java", "-jar", "/root/tiedyer-backend.jar", "--spring.profiles.active=prod"]

# 暴露容器的端口
EXPOSE 7576

# 构建命令
# docker build -t tiedyer/backend:1.0 .

# 运行命令
# docker run -d -p 7576:7576  --network=tiedyer  --name=tiedyer-backend  -v /home/tiedyer/resource:/root/resource tiedyer/backend:1.0