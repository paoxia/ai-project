# ai-project

## 简介

### 数据/模型

- 法律文件来源:https://github.com/LawRefBook/Laws
- 接入高德天气接口
- 接入腾讯混元大模型

### 实现功能

- ElasticSearch+混元大模型实现简单法律RAG
- SpringAI MCP实现查询天气

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
# ES启动失败的话可能是容量不够了，可以调小，"ES_JAVA_OPTS=-Xms256M -Xmx256M"
# kibana console
# http://localhost:25601/

```

## RAG

```
PUT /rag-law-index
{
  "mappings": {
    "properties": {
      "content": {
        "type": "text"
      },
      "vector": {
        "type": "dense_vector",
        "dims": 1024
      }
    }
  }
}
```

## MCP

本地启动后，使用cursor编辑MCP server连接

```json
{
  "mcpServers": {
    "localhost": {
      "url": "http://localhost:8080/sse"
    }
  }
}
```
