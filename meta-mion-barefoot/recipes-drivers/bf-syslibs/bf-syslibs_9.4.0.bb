# SPDX-License-Identifier: MIT

LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           file://0001-Update-log-directory.patch \
           "
SRCREV = "${AUTOREV}"

DEPENDS += " linux-yocto"

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "${datadir}/bfsys/*"

inherit autotools sde-package-extract

