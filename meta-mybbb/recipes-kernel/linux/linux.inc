SUMMARY = "Kernel for BeagleBone Black"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-${PV}.tar.xz \
    file://defconfig \
    "

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "${PV}"
LINUX_VERSION_EXTENSION:append = "-lucas"

S="${WORKDIR}/linux-${LINUX_VERSION}"

COMPATIBLE_MACHINE = "beaglebone"
KCONFIG_MODE = "alldefconfig"