# 基础镜像
FROM openjdk:11.0-jre-buster

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 拷贝jar包到镜像中
COPY personal-blog.jar /app.jar


# 启动命令
CMD ["java", "-jar", "/app.jar"]