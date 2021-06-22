# SPDX-License-Identifier: MIT

LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           file://json_targetdir_correction.patch \
"

SRCREV = "${AUTOREV}"
SDE_VERSION = "9.4.0"
BSP_PLATFORM_CODE ?= "bf-reference-bsp-9.4.0-BF2556_1.0.2"

DEPENDS += "bf-kdrv bf-drivers bf-syslibs libusb1 curl unzip-native"

do_unpack[depends] += "unzip-native:do_populate_sysroot"
inherit autotools-brokensep gettext

TARGET_CFLAGS += "-Wno-error "

EXTRA_OEMAKE += "-C ${SRC}/bf-platforms-${SDE_VERSION}"
INSANE_SKIP_${PN} += " file-rdeps"

# The default flags makes the BSP unable to find some shared libs
SECURITY_LDFLAGS = ""
AUTOTOOLS_SCRIPT_PATH ?= "${SRC}/bf-platforms-${SDE_VERSION}"
S="${WORKDIR}/git/bf-platforms-${SDE_VERSION}"
SRC="${WORKDIR}/git"
BSP="${SRC}/bf-reference-bsp-9.4.0"
SDE_INSTALL="${SRC}/bf-sde-${SDE_VERSION}"
BSP_INSTALL="$SDE_INSTALL"

extract() {
    # Extract BSP
    export BSP=${BSP}
    export SDE_INSTALL=${SDE_INSTALL}
    export BSP_INSTALL=${BSP_INSTALL}
    cd ${SRC}
    tar xzvf ${SRC}/bf-sde-${SDE_VERSION}.tgz                                               
    tar xzvf ${BSP}.tgz
    tar xzvf ${BSP}/packages/bf-platforms-${SDE_VERSION}.tgz
    cd ${BSP}
    ./extract_all.sh
    cd ${SRC}
    unzip ${SRC}/${BSP_PLATFORM_CODE}.zip -d ${SRC}/bf-platforms-${SDE_VERSION}
    cd ${SRC}/bf-platforms-${SDE_VERSION}
    #patch -p1 < ./platforms/bf2556x_1t.diff

    # There is a bunch of cruft in how this is packaged.
    # Removing all the cruft
    find ${S} -name Makefile.in -delete
    find ${S} -name Makefile -delete
    find ${S} -name "*.o" -delete
    find ${S} -name ".deps" -exec rm -rv {} +
    find ${S} -name ".libs" -exec rm -rv {} +
    find ${S} -name "*.la" -delete
    find ${S} -name "*.lo" -delete
    rm ${S}/configure
}

do_configure_prepend() {
    export BSP=${BSP}
    export SDE_INSTALL=${SDE_INSTALL}
    export BSP_INSTALL=${BSP_INSTALL}
    autoreconf
    autoconf
}

do_compile_prepend() {
    export BSP=${BSP}
    export SDE_INSTALL=${SDE_INSTALL}
    export BSP_INSTALL=${BSP_INSTALL}
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}

FILES_${PN} += "${datadir}"
