# SPDX-License-Identifier: MIT

LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           "

SRCREV = "${AUTOREV}"

DEPENDS += " bf-python bf-syslibs bf-utils thrift thrift-native grpc grpc-native pi"

PREFERRED_VERSION_gprc = "1.25.0"

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "${libdir}/*.so.*"
FILES_${PN} += "${libdir}/${PYTHON_DIR}/*"
FILES_${PN} += "${datadir}/*"
FILES_${PN}-dev += "${libdir}/*.so"

inherit autotools sde-package-extract pythonnative python3-dir python3native

EXTRA_OECONF += "enable_thrift=no enable_grpc=yes enable_bfrt=yes enable_p4rt=yes enable_pi=yes --without-kdrv "

EXTRA_OEMAKE += "KDIR=${STAGING_KERNEL_DIR}"


TARGET_CFLAGS += " \
    -Wno-stringop-truncation \
    -Wno-stringop-overflow \
    -Wno-absolute-value \
    "
TARGET_CXXFLAGS_remove = " \
    -Wno-absolute-value \
"

do_compile_prepend() {
    install -d ${STAGING_DIR_TARGET}${libdir}/
    ln -sf ${S}/libavago.${TARGET_ARCH}.so ${STAGING_DIR_TARGET}${libdir}/libavago.so
}

do_install_append() {
    install -d ${D}${libdir}/
    install -m 0755 ${S}/libavago.${TARGET_ARCH}.so ${D}${libdir}/libavago.so.0
    ln -sf --relative ${D}${libdir}/libavago.so.0 ${D}${libdir}/libavago.so
    install -d ${D}/usr/share/tofino_sds_fw/avago/firmware
    cp -av ${S}/src/avago/firmware/*rom ${D}/usr/share/tofino_sds_fw/avago/firmware 
    chown -R root:root ${D}/usr/share/tofino_sds_fw/avago/firmware
    # Remove because PI installs this as well
    rm -rf ${D}${includedir}/google
    # Remove because barefoot-bsp provides
    rm -rf ${D}${libdir}/python3.4
    install -d ${D}/var/log/ptne/
}

FILES_${PN}_append = "/var/log/ptne/ \
                      /usr/share/"
