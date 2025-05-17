# ai-project

## 构建

```bash
# package
mvn clean package

# build image
docker build -t ai-project .

# run container use image
docker run -p 20080:8080 ai-project

```