LICENSE = "CLOSED"

# Set PTNE_TAG and PTNE_BRANCH in local.conf
TAG = "tag=${PTNE_TAG}"
BRANCH = "branch=${PTNE_BRANCH}"

SRC_URI = "git://git@stash.corp.saab.se:7999/ptne/ptne-platform.git;protocol=ssh;${TAG};${BRANCH} \
          "

S = "${WORKDIR}/git/tofino/stordis/drivers/mv_pipe_config"

TGT_DIR = "/delta"

FILES_${PN} += "${TGT_DIR}/*"

# Remove -O2 from gcc command
FULL_OPTIMIZATION = "-pipe ${DEBUG_FLAGS}"

do_compile() {
    oe_runmake mv_pipe_config
}

do_install() {
	install -d ${D}${TGT_DIR}
	install -m 0755 mv_pipe_config ${D}${TGT_DIR}/mv_pipe_config
}
