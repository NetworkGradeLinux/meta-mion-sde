# SPDX-License-Identifier: MIT

LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

FILES_${PN} += "${datadir}/p4c"

INSANE_SKIP_${PN} += "already-stripped file-rdeps"

DEPENDS_class-native += "python3-packaging-native python3-jsl-native python3-jsonschema-native"

RDEPENDS_${PN} += "python3-packaging python3-jsl python3-jsonschema"

BBCLASSEXTEND = "native "

inherit sde-package-extract

CLEANBROKEN = "1"

configure_common() {
    tar xvf ${BPN}-${PV}/p4c-${PV}.${ARCH}.tar.bz2
}

do_configure_prepend () {
    export ARCH=${TARGET_ARCH}
    configure_common
}

do_configure_class-native_prepend () {
    export ARCH=${BUILD_ARCH}
    configure_common
}

do_compile_class-native() {
 # Patching out hardcoded preprocessor nonsense
 sed -i "s/preprocessor', 'cc'/preprocessor', os.environ\['CC'\].split\(' '\)\[0\]/g" ${S}/p4c-${PV}.${BUILD_ARCH}/share/p4c/p4c_src/barefoot.py
}

install_common() {
    install -d ${D}${bindir}
    install -d ${D}${datadir}
    cp -r --preserve=mode ${S}/p4c-${PV}.${ARCH}/bin/* ${D}${bindir}
    cp -r --preserve=mode ${S}/p4c-${PV}.${ARCH}/share/* ${D}${datadir}/
}


do_install() {
    export ARCH=${TARGET_ARCH}
    install_common
}


do_install_class-native() {
    export ARCH=${BUILD_ARCH}
    install_common
}

