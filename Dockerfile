# 使用官方 Java 17 镜像
FROM openjdk:17-jdk-slim

# 设置容器工作目录
WORKDIR /app

# 复制主模块构建好的 jar 包
COPY ai-start/target/ai-start-0.0.1-SNAPSHOT.jar app.jar

# 设置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
