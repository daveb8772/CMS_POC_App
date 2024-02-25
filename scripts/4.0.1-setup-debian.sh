#!/bin/bash
# setup instructions to a VM with debian
# copy to remote debian and run
# run: su
# run: sudo visudo
#  add: username   ALL=(ALL) NOPASSWD: ALL
# sudo usermod -aG docker super
# copy .zshrc exports from other super servers
# in local change /etc/hosts and add the remote IP and alias for super
# Add: 192.168.2.173 prometheus.myapp.local
# disable password login:
# at local generate: ssh-keygen -t rsa
# then: ssh-copy-id username@server_ip
# after success disable: sudo nano /etc/ssh/sshd_config
# PasswordAuthentication no
# Change port

# Update package lists
sudo apt-get update

# Install general utilities
sudo apt-get install -y \
    git \
    curl \
    wget \
    vim \
    nano \
    tilix \
    zsh \
    iptables \
    htop

# Install development tools
sudo apt-get install -y \
    build-essential \
    cmake

# Install Kubernetes tools
sudo apt-get install -y apt-transport-https gnupg2
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee -a /etc/apt/sources.list.d/kubernetes.list
sudo apt-get update
sudo apt-get install -y kubectl

# Install k3s - lightweight Kubernetes
curl -sfL https://get.k3s.io | sh -

# Install Docker
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    gnupg-agent \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository \
    "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo docker run hello-world
# Configure Docker to start on boot
sudo systemctl enable docker

# Install Oh My Zsh for a better Zsh configuration
sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

# Note: k3s installation script will automatically download and install k3s.
# Additional configurations or installations (like specific versions) may require manual steps.

echo "Installation of specified tools completed."