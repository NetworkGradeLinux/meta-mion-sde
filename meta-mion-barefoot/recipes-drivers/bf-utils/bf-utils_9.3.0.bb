LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           "
SRCREV = "${AUTOREV}"

DEPENDS += " bf-syslibs"

FILES_${PN} += "${datadir}/cli"
FILES_${PN} += "${libdir}/bfshell_plugin_clish.so"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools sde-package-extract

EXTRA_OECONF += "--disable-bf-python"

# Other bf packages want to link with the static lib, so keep that
DISABLE_STATIC = ""

