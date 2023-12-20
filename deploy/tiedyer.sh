#!/bin/sh

echo " _______ _          _"
echo "|__   __(_)        | |"
echo "   | |   _  ___  __| |_   _  ___ _ __"
echo "   | |  | |/ _ \/ _\` | | | |/ _ \ '__|"
echo "   | |  | |  __/ (_| | |_| |  __/ |"
echo "   |_|  |_|\___|\__,_|\__, |\___|_|"
echo "                       __/ |"
echo "                      |___/"
echo "Program is starting..."
echo "----------------------------------------"

# 检查Docker Engine是否安装
echo "Checking Docker Engine..."
docker -v
if [ $? -eq 0 ]; then
    echo "Docker Engine is already installed!"
else
    echo "Installing Docker Engine..."
    curl -sSL https://get.docker.com/ | sh
    echo "Installing Docker Engine completed!"
fi
echo "----------------------------------------"

# 检查Docker Compose是否安装
echo "Checking Docker Compose..."
docker compose version
if [ $? -eq 0 ]; then
    echo "Docker Compose is already installed!"
else
    echo "Installing Docker Compose..."
    sudo apt-get update
    sudo apt-get install docker-compose
    echo "Installing Docker Compose completed!"
fi
echo "----------------------------------------"

# 读取.env中定义的Redis密码
redis_password=$(grep "^REDIS_PASSWORD=" "./.env" | cut -d'=' -f2)

# 替换redis.conf中的密码
sed -i "s/^requirepass .*/requirepass $redis_password/" "./conf/redis/redis.conf"

# 获取当前脚本所在目录的上上级目录
parent_dir=$(dirname "$(dirname "$(pwd)")")
echo "Parent directory is $parent_dir"
echo "----------------------------------------"

# 通过sed命令替换.env文件中的PROJECT_LOCATION
sed -i "s|PROJECT_LOCATION=.*|PROJECT_LOCATION=$parent_dir|g" "./.env"
# 替换nginx.conf中的路径
sed -i "s|/hom/tiedyer/|$parent_dir/tiedyer/|g" "./conf/nginx/nginx.conf"

# 创建目录结构
echo "Start creating directories...."
mkdir -p "$parent_dir/mysql/data" && echo "Created $parent_dir/mysql/data directory"
mkdir -p "$parent_dir/mysql/conf" && echo "Created $parent_dir/mysql/conf directory"
mkdir -p "$parent_dir/mysql/init" && echo "Created $parent_dir/mysql/init directory"
mkdir -p "$parent_dir/nginx/html" && echo "Created $parent_dir/nginx/html directory"
mkdir -p "$parent_dir/redis/data" && echo "Created $parent_dir/redis/data directory"
mkdir -p "$parent_dir/tiedyer/resource" && echo "Created $parent_dir/tiedyer/resource directory"
echo "Creating directories completed!"
echo "----------------------------------------"


# 复制文件
echo "Start copying files..."
cp "./conf/mysql/my.cnf" "$parent_dir/mysql/conf/" && echo "my.cnf copied to $parent_dir/mysql/conf/"
cp "./conf/mysql/init.sql" "$parent_dir/mysql/init/" && echo "init.sql copied to $parent_dir/mysql/init/"
cp "./conf/redis/redis.conf" "$parent_dir/redis/" && echo "redis.conf copied to $parent_dir/redis/"
cp "./conf/nginx/nginx.conf" "$parent_dir/nginx/" && echo "nginx.conf copied to $parent_dir/nginx/"
echo "Files copying completed!"
echo "----------------------------------------"

# 检查docker-compose.yml 文件
docker-compose config -q


# 构建镜像
echo "Start building Docker Images"
docker compose build
echo "Building Docker Images completed!"
echo "----------------------------------------"

# 启动服务
echo "Starting services"
docker compose up -d
echo "Service started successfully!"