LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://${MIONBASE}/bf-sde; \
           file://0001-Generalize-Makefile.patch;striplevel=8 \
           "
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/bf_kdrv"

inherit module

MODULE_NAME = "bf_kdrv"

KERNEL_MODULE_AUTOLOAD += "${MODULE_NAME}"
KERNEL_MODULE_PROBECONF += "${MODULE_NAME}"
module_conf_${MODULE_NAME} = "options ${MODULE_NAME} intr_mode=msi"

FILES_${PN} += "${sysconfdir}/modules-load.d/${MODULE_NAME}.conf"

extract() {
    # Extract SDE
    tar xzf ${WORKDIR}/git/bf-sde-${PV}.tgz
    # Extract package
    tar xzf bf-sde-${PV}/packages/bf-drivers-${PV}.tgz bf-drivers-${PV}/kdrv/bf_kdrv --strip-components=2
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}
