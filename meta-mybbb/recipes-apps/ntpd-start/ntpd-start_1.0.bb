SUMMARY = "Script to start ntpd client service"

LICENSE = "CLOSED"

SRC_URI = "file://ntpd"

inherit update-rc.d

INITSCRIPT_NAME = "ntpd"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/ntpd ${D}${sysconfdir}/init.d/ntpd
}