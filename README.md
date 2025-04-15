# README

项目需求环境:

- java17
- mybatis8

    ```
    docker run --name jieax-mysql \
    -p 3306:3306 \
    -e MYSQL_USER=jieax \
    -e MYSQL_PASSWORD=jieax \
    -e MYSQL_ROOT_PASSWORD=jieax \
    --restart=always  \
    -d \
    mysql:8.0
    ```

- mongodb

    ```
    docker run -d --name jieax-mongodb \
    -p 27017:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=jieax \
    -e MONGO_INITDB_ROOT_PASSWORD=jieax \
    --restart=always  \
    -d \
    mongo:4.4.4
    ```
