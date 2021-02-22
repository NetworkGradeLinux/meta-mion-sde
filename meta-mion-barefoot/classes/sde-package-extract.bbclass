extract() {
    # Extract SDE
    tar xzf ${WORKDIR}/git/bf-sde-${PV}.tgz
    # Extract package
    tar xzf bf-sde-${PV}/packages/${BPN}-${PV}.tgz
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}
