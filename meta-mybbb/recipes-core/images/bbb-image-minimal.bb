inherit core-image

DESCRIPTION = "BBB minimal image"

TOOLCHAIN_HOST_TASK += "packagegroup-rust-cross-canadian-${MACHINE}"

IMAGE_FEATURES:append = " ssh-server-dropbear"

IMAGE_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-core-ssh-dropbear \
    os-release \
    ntpd-start \
    packagegroup-bbb-tools \
"

LICENSE = "CLOSED"

