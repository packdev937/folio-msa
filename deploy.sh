#!/bin/bash

# Docker install
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed..."
    echo "Docker install start..."
    sudo apt-get update -y
    sudo apt-get install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt-get update -y
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io
    echo "Docker install complete"
else
    echo "Docker is already installed"
fi

# Docker-compose install
if ! command -v docker-compose &> /dev/null; then
    echo "Docker-compose is not installed..."
    echo "Docker-compose install start..."
    sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    echo "Docker-compose install complete!"
else
    echo "Docker-compose is already installed"
fi

# Define container names
CONFIG_CONTAINER="config-service"
GATEWAY_CONTAINER="gateway-service"
USER_CONTAINER="user-service"
PHOTO_CONTAINER="photo-service"
QR_CONTAINER="qr-service"
FEED_CONTAINER="feed-service"
FOLLOW_CONTAINER="follow-service"

# Start services
echo "Starting services..."
docker-compose -f /home/ec2-user/docker-compose.yml up -d $CONFIG_CONTAINER $GATEWAY_CONTAINER $USER_CONTAINER $PHOTO_CONTAINER $QR_CONTAINER $FEED_CONTAINER $FOLLOW_CONTAINER

# Wait for the services to be healthy before proceeding
sleep 10


