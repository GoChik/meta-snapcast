SUMMARY = "Shairport Sync"
DESCRIPTION = "Shairport Sync is an AirPlay audio player"
LICENSE = "MIT"

DEPENDS += " \
    popt \
    libconfig \
    avahi \
    alsa-lib \
    openssl \
"

PV = "4.0-dev"
S = "${WORKDIR}/git"

SRC_URI = " \
    git://github.com/mikebrady/shairport-sync.git;protocol=https;branch=development;tag=${PV} \
    file://shairport-sync.service \
"
LIC_FILES_CHKSUM = "file://../git/LICENSES;md5=9f329b7b34fcd334fb1f8e2eb03d33ff"

inherit autotools systemd pkgconfig

PACKAGECONFIG ??= "stdout"
PACKAGECONFIG[systemd] = ""
PACKAGECONFIG[stdout] = "--with-pipe --with-stdout,"

EXTRA_OECONF="--with-alsa --with-avahi --with-ssl=openssl --with-metadata"

# systemd unit file is not enabled by default because snapcast launches shairport-sync manually
SYSTEMD_SERVICE_${PN} = "${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'shairport-sync.service', '', d)}"

do_install_prepend() {
    cp ${S}/scripts/${PN}.conf ${WORKDIR}/build/scripts/

    if ${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${S}/../shairport-sync.service ${D}${systemd_system_unitdir}
    fi
}