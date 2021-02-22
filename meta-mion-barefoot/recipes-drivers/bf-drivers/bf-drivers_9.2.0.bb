LICENSE = "CLOSED"

SRC_URI = "git://${MIONBASE}/bf-sde \
           file://sde_mac_learning_aging_crash.patch \
           file://0001-Avoid-shared_ptr-ambiguity.patch \
           file://0001-Fix-incomplete-install-path.patch \
           "

SRCREV = "${AUTOREV}"

DEPENDS += " bf-python bf-syslibs bf-utils thrift thrift-native grpc grpc-native pi"

PREFERRED_VERSION_gprc = "1.25.0"

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "${libdir}/*.so.*"
FILES_${PN} += "${libdir}/${PYTHON_DIR}/*"
FILES_${PN} += "${datadir}/*"
FILES_${PN}-dev += "${libdir}/*.so"

inherit autotools sde-package-extract python3-dir python3native

EXTRA_OECONF += "enable_thrift=yes enable_grpc=yes enable_bfrt=no enable_p4rt=yes enable_pi=yes --without-kdrv "

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
    # Remove because PI installs this as well
    rm -rf ${D}${includedir}/google
}
