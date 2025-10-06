# Yocto

`local.conf`

```ini
INHERIT += "rm_work"
RM_WORK_EXCLUDE += ""

# MACHINE="beaglebone-yocto"
MACHINE="beaglebone"
```

Build image: `bitbake bbb-image-minimal`

Flash the SD card: `bunzip2 -c build/tmp/deploy/images/beaglebone/bbb-image-minimal-beaglebone.rootfs.wic.bz2 | sudo dd of=/dev/sdX bs=4M conv=fsync`

## Know issues

Warning with kernel 6.17: `net/8021q/vlan_core.c` function `vlan_for_each()` assert `ASSERT_RTNL()` failed.

```
Error: argument "/en*" is wrong: "dev" not a valid ifname
Starting system message bus: dbus.
Starting Dropbear SSH server: Generating 2048 bit rsa key, this may take a while...
Public key portion is:
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDiptFY4ZSciRJMLSgBGStDOruPupjqyx1a0gL6r3He6Ri9qywCvD7LYFdkTf1phGMPQ2sUDEPRueaMwgJhCmV2zMKjelucmbtWRreYtodZ2J+kcPM8k/rVxVxZSEtzbHsoILibu9p8k8gEVVmIXcQZvUBa83omDa5b6JxIqOnRVIjYIi9VBlxC4gIIMPl6x3YYrLuN/9ekVrTxRgNFTuTNoptAgemLI5vMqUa4Q2kvUCgRWouixjS03/0VRbuNxHU0FS0LQIhykFHVlR2IaaaWM0OPJf+upkmAEAMdCkPrKcTD7oZc5tpTMUrdHoOIhQJ1J18lEW147iP13jE/qS0t root@beaglebone
Fingerprint: SHA256:gPOW4Tvl/PW8lQX7FNEEcs62ujDVlpaApkBSRP01Z3s
dropbear.
Starting rpcbind daemon...done.
[   24.769374] ------------[ cut here ]------------
[   24.774195] WARNING: CPU: 0 PID: 337 at /net/8021q/vlan_core.c:236 vlan_for_each+0x54/0xb8
[   24.782593] RTNL: assertion failed at /net/8021q/vlan_core.c (236)
[   24.788809] Modules linked in: snd_soc_simple_card snd_soc_simple_card_utils snd_soc_davinci_mcasp snd_soc_hdmi_codec snd_soc_ti_udma snd_soc_ti_edma snd_soc_ti_sdma snd_soc_core snd_pcm_dmaengine snd_pcm snd_timer snd soundcore
[   24.809287] CPU: 0 UID: 998 PID: 337 Comm: rpcbind Not tainted 6.17.0-yocto-standard-lucas #1 PREEMPT
[   24.818650] Hardware name: Generic AM33XX (Flattened Device Tree)
[   24.824774] Call trace:
[   24.824791]  unwind_backtrace from show_stack+0x18/0x1c
[   24.832614]  show_stack from dump_stack_lvl+0x38/0x48
[   24.837715]  dump_stack_lvl from __warn+0x88/0x12c
[   24.842556]  __warn from warn_slowpath_fmt+0x11c/0x13c
[   24.847738]  warn_slowpath_fmt from vlan_for_each+0x54/0xb8
[   24.853359]  vlan_for_each from cpsw_add_mc_addr+0x44/0xa0
[   24.858891]  cpsw_add_mc_addr from __hw_addr_ref_sync_dev+0xd0/0xf0
[   24.865207]  __hw_addr_ref_sync_dev from __dev_mc_add+0x54/0x68
[   24.871169]  __dev_mc_add from igmp6_group_added+0x80/0xf4
[   24.876701]  igmp6_group_added from __ipv6_dev_mc_inc+0x1bc/0x1ec
[   24.882837]  __ipv6_dev_mc_inc from __ipv6_sock_mc_join+0x13c/0x190
[   24.889147]  __ipv6_sock_mc_join from do_ipv6_setsockopt+0xd24/0xf78
[   24.895550]  do_ipv6_setsockopt from ipv6_setsockopt+0x78/0xbc
[   24.901424]  ipv6_setsockopt from udpv6_setsockopt+0x54/0x5c
[   24.907129]  udpv6_setsockopt from sock_common_setsockopt+0x34/0x44
[   24.913450]  sock_common_setsockopt from do_sock_setsockopt+0x178/0x190
[   24.920111]  do_sock_setsockopt from __sys_setsockopt+0x8c/0xcc
[   24.926071]  __sys_setsockopt from ret_fast_syscall+0x0/0x54
[   24.931770] Exception stack(0xe00f1fa8 to 0xe00f1ff0)
[   24.936855] 1fa0:                   00000014 004c2d84 00000004 00000029 00000014 bea4fbe8
[   24.945078] 1fc0: 00000014 004c2d84 00000004 00000126 00000014 004c3b84 00000000 004c2c44
[   24.953297] 1fe0: 004c2e58 bea4fbb0 004bfb8c b6e62d64
[   24.958490] ---[ end trace 0000000000000000 ]---
Starting bluetooth: bluetoothd.
Starting Network Time Protocol client Daemon: ntpd.
Starting syslogd/klogd: done
 * Starting Avahi mDNS/DNS-SD Daemon: avahi-daemon
   ...done.
Starting Telephony daemon
Starting Linux NFC daemon

Poky (Yocto Project Reference Distro) 5.0.9 beaglebone /dev/ttyS0
```