LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a2f59868b389d66faed0cf18e0caa486"

SRC_URI = "${SOURCEFORGE_MIRROR}/${BPN}/Judy-${PV}.tar.gz \
           file://0001-Don-t-build-doc-test.patch \
           "

SRC_URI_append_class-target = "file://0001-Use-JudyLTablesGen-on-PATH-instead-of-local.patch"

SRC_URI[sha256sum] = "d2704089f85fdb6f2cd7e77be21170ced4b4375c03ef1ad4cf1075bd414a63eb"

BBCLASSEXTEND = "native"

DEPENDS_class-target = "judy-native"

inherit autotools-brokensep

do_install_append_class-native() {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/${BPN}-${PV}/src/JudyL/JudyLTablesGen ${D}/${bindir}/JudyLTablesGen
    install -m 0755 ${WORKDIR}/${BPN}-${PV}/src/Judy1/Judy1TablesGen ${D}/${bindir}/Judy1TablesGen
}
