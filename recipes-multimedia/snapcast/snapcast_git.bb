SUMMARY = "Snpcast"
DESCRIPTION = "Snapcast is a multiroom client-server audio player"
LICENSE = "GPLv3"

DEPENDS += " \
    avahi \
    boost \
    alsa-lib \
    flac \
    libvorbis \
"

PV = "0.22.0"
S = "${WORKDIR}/git"

SRC_URI = " \
    git://github.com/badaix/snapcast.git;protocol=https;tag=v${PV} \
    file://snapclient.service \
    file://snapserver.service \
    file://snapserver.conf \
"
LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=7702f203b58979ebbc31bfaeb44f219c"

inherit cmake systemd

EXTRA_OECMAKE = "-DBUILD_TESTS=OFF -DBUILD_STATIC_LIBS=OFF"

PACKAGES = " \
    ${PN}-client \
    ${PN}-server \
    ${PN}-dbg \
    ${PN}-client-doc \
    ${PN}-server-doc \
"

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/snapclient.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/snapserver.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/snapserver.conf ${D}${sysconfdir}/
}

SYSTEMD_PACKAGES = "${PN}-client ${PN}-server"
SYSTEMD_SERVICE_${PN}-client = "snapclient.service"
SYSTEMD_SERVICE_${PN}-server = "snapserver.service"

FILES_${PN}-client = " \
    ${bindir}/snapclient \
    ${systemd_system_unitdir}/snapclient.service \
"

FILES_${PN}-client-doc = "${mandir}/man1/snapclient*"

FILES_${PN}-server = " \
    ${bindir}/snapserver \
    ${sysconfdir}/snapserver* \
    ${datadir}/snapserver/* \
    ${systemd_system_unitdir}/snapserver.service \
"

FILES_${PN}-server-doc = "${mandir}/man1/snapserver*"