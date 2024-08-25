SUMMARY = "Kernel for BeagleBone Black"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-6.6.35.tar.xz \
    file://defconfig \
    "
SRC_URI[sha256sum] = "fce3ee728712ed063aa8c14a8756c8ff8c7a46ba3827f61d2b04a73c7cf5dd9e"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"


LINUX_VERSION ?= "6.6.35"
LINUX_VERSION_EXTENSION:append = "-lucas"

S="${WORKDIR}/linux-${LINUX_VERSION}"

PV = "${LINUX_VERSION}"

COMPATIBLE_MACHINE = "beaglebone"
KCONFIG_MODE = "alldefconfig"