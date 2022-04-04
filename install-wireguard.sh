#!/bin/bash
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

function isRoot() {
	if [ "${EUID}" -ne 0 ]; then
		echo "You need to run this script as root"
		exit 1
	fi
}

function installWireGuard() {
	# Install WireGuard tools and module
	apt-get update
	apt-get install -y wireguard iptables
	# Убедитесь, что каталог существует (это не относится к Fedora)
	chmod 600 -R /etc/wireguard/

	# Включить маршрутизацию на сервере
	echo "net.ipv4.ip_forward = 1" >> /etc/sysctl.conf
    sysctl --system
	systemctl start wg-quick@wg0
	systemctl enable wg-quick@wg0
}

# Check for root, virt, OS...
isRoot
installWireGuard
