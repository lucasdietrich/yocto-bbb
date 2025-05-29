SUMMARY = "BBB debug tools"
LICENSE = "CLOSED"

inherit packagegroup

# populate here
RDEPENDS:${PN} = "\
    packagegroup-core-ssh-dropbear \
    libmosquitto1 \
    mosquitto-clients \
    openssl \
    mbedtls \
    tzdata \
    htop \
    curl \
    nano \
    tree \
    canutils \
    strace \
    dtc \
    gdbserver \
    git \
    bash-completion \
    e2fsprogs \
    e2fsprogs-resize2fs \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    minicom \
    mmc-utils \
"