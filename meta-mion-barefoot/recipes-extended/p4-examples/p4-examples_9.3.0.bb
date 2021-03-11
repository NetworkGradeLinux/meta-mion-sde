LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde"

SRCREV = "${AUTOREV}"



DEPENDS += " linux-yocto"

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "${datadir}/p4/*"

EXTRA_OECONF += " --datadir=${D}/usr/share"

inherit autotools sde-package-extract

