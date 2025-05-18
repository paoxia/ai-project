# ai-project

## 构建

```bash
# package
mvn clean package

# build image
docker build -t ai-project .

# run container use image
docker run --name ai-project-service -p 20080:8080 ai-project 

```

## elastic search and kibana

```bash
docker-compose up -d

# kibana console
# http://localhost:25601/app/dev_tools#/console

```