
## Server

- Cloud instance at Hetzner, IP 62.238.104.244
- DNS at AWS Route53 (Vilect account): merjemterud.torshov.club
- Ubuntu 26.04.1 LTS, short-lived (~1 week), runs Azuracast for the festival radio

### Hardening (2026-08-31)

SSH is key-only via `/etc/ssh/sshd_config.d/10-hardening.conf`.
Hetzner's image shipped with `PasswordAuthentication yes`; that was the one real gap.

```
PasswordAuthentication no      KbdInteractiveAuthentication no
AuthenticationMethods publickey    PermitRootLogin prohibit-password
MaxAuthTries 3                 LoginGraceTime 20
X11Forwarding no               AllowAgentForwarding no
```

ufw enabled, default-deny inbound, IPv6 included.
Open: 22, 80, 443 (tcp+udp), 8000-8999/tcp (station streams).

Left as-is because already correct: root has no password (`passwd -S root` → `L`),
no non-root accounts, `unattended-upgrades` enabled with security origins,
nothing listening but SSH (DNS and chrony are loopback-only).

fail2ban skipped — with password auth off it only cuts log noise.

Hetzner Cloud Firewall added, inbound rules below.
It sits outside the VM, so it also covers the Docker-published ports that ufw does not.

| Protocol | Port | Source | Description |
|---|---|---|---|
| TCP | 22 | Any IPv4, Any IPv6 | — |
| TCP | 80 | Any IPv4, Any IPv6 | HTTP |
| TCP | 443 | Any IPv4, Any IPv6 | HTTPS |
| UDP | 443 | Any IPv4, Any IPv6 | HTTP3 |
| TCP | 8000-8999 | Any IPv4, Any IPv6 | Listener streams |

SSH is open to the world rather than to a single admin IP.
2022/tcp (SFTP) is closed.
