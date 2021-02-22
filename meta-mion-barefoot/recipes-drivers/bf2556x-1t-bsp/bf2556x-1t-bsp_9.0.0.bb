LICENSE = "CLOSED"

# Set PTNE_TAG and PTNE_BRANCH in local.conf
TAG = "tag=${PTNE_TAG}"
BRANCH = "branch=${PTNE_BRANCH}"

SRC_URI = "git://git@stash.corp.saab.se:7999/ptne/ptne-platform.git;protocol=ssh;${TAG};${BRANCH} \
           file://bsp_port_info_callback.patch;patchdir=${WORKDIR}/bf-platforms-${PV};striplevel=2 \
           file://bsp_segfault.patch;patchdir=${WORKDIR}/bf-platforms-${PV};striplevel=2 \
           file://dont_treat_warnings_as_errors.patch;patchdir=${WORKDIR}/bf-platforms-${PV};striplevel=2 \
           file://0001-Remove-absolute-paths-to-avoid-build-host-contaminat.patch \
           "

DEPENDS += "bf-drivers bf-syslibs libusb1 curl"

S = "${WORKDIR}/bf-platforms-${PV}"

PKG_DIR = "${WORKDIR}/git/tofino/stordis/"

inherit autotools

EXTRA_OECONF += "--with-tof-brgup-plat"

INSANE_SKIP_${PN} += " file-rdeps"

# The default flags makes the BSP unable to find some shared libs
SECURITY_LDFLAGS = ""

extract() {
    rm -rf BF2556X-1T_BSP_${PV}-master
    rm -rf bf-platforms-${PV}
    # Extract BSP
    unzip -o ${PKG_DIR}/BF2556X-1T_BSP_${PV}-master.zip
    tar xzf BF2556X-1T_BSP_${PV}-master/packages/bf-platforms-${PV}.tgz
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}
