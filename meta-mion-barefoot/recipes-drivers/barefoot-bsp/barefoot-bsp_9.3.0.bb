LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           file://0001-Remove-host-includes.patch;patchdir=${WORKDIR}/git/bf-platforms-${SDE_VERSION}"

SRCREV = "${AUTOREV}"
SDE_VERSION = "9.3.0"
BSP_PLATFORM_CODE ?= "bf-reference-bsp-9.3.0-BF2556_1c5723d"

DEPENDS += "bf-kdrv bf-drivers bf-syslibs libusb1 curl unzip-native"

do_unpack[depends] += "unzip-native:do_populate_sysroot"

inherit autotools-brokensep

TARGET_CFLAGS += "-Wno-error "

EXTRA_OECONF += "--prefix=${BSP_INSTALL} --with-tof-brgup-plat"
EXTRA_OEMAKE += "-C ${SRC}/bf-platforms-${SDE_VERSION}"
INSANE_SKIP_${PN} += " file-rdeps"

# The default flags makes the BSP unable to find some shared libs
SECURITY_LDFLAGS = ""
AUTOTOOLS_SCRIPT_PATH ?= "${SRC}/bf-platforms-${SDE_VERSION}"
S="${WORKDIR}/git/bf-platforms-${SDE_VERSION}"
SRC="${WORKDIR}/git"
BSP="${SRC}/bf-reference-bsp-9.3.0"
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
    unzip ${SRC}/${BSP_PLATFORM_CODE}.zip -d ${SRC}/bf-platforms-${SDE_VERSION}/platforms
    cd ${SRC}/bf-platforms-${SDE_VERSION}
    patch -p1 < ./platforms/bf2556x_1t.diff
}

do_configure_prepend() {
    export BSP=${BSP}
    export SDE_INSTALL=${SDE_INSTALL}
    export BSP_INSTALL=${BSP_INSTALL}i
}

do_compile_prepend() {
    export BSP=${BSP}
    export SDE_INSTALL=${SDE_INSTALL}
    export BSP_INSTALL=${BSP_INSTALL}
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}

