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
    e2fsprogs-resize2fs \
    python3 \
    python3-pytz \
    python3-aiohttp \
    python3-requests \
    python3-requests-oauthlib \
    python3-pyopenssl \
    minicom \
"