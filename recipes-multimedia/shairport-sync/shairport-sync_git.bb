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
"
LIC_FILES_CHKSUM = "file://../git/LICENSES;md5=9f329b7b34fcd334fb1f8e2eb03d33ff"

inherit autotools systemd pkgconfig

EXTRA_OECONF="--with-alsa --with-avahi --with-ssl=openssl --with-metadata --with-stdout --with-pipe"

do_install_prepend() {
    cp ${S}/scripts/${PN}.conf ${WORKDIR}/build/scripts/
}