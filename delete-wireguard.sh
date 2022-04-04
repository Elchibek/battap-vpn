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

function uninstallWg() {
		systemctl stop "wg-quick@${SERVER_WG_NIC}"
		systemctl disable "wg-quick@${SERVER_WG_NIC}"
		apt-get autoremove --purge -y wireguard

		rm -rf /etc/wireguard
		rm -f /etc/sysctl.d/wg.conf
		# Reload sysctl
		sysctl --system
}

# Check for root, virt, OS...
isRoot
uninstallWg
