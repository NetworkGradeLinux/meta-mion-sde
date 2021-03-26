# SPDX-License-Identifier: MIT

DEPENDS += "p4-compilers-native"

EXTRA_OEP4 ?= ""
P4COMPILER ?= "p4c"

# Skip the unwanted steps
do_configure[noexec] = "1"
p4_do_compile() {
        p4input=$(find ${S} -name *.p4| head -n 1)
        p4file=$(basename -- ${p4input})
        p4name="${p4file%.*}"
	${STAGING_BINDIR_NATIVE}/${P4COMPILER} --arch=${P4ARCH} --target=${P4TARGET} ${p4input} -I ${STAGING_DIR_NATIVE}/share/${P4COMPILER}/p4include ${EXTRA_OEP4} -o ${B}${prefix}/${p4name}.out --p4runtime-files=${p4file}info.txt  || \
	die "p4 compile execution failed."
}

p4_do_install() {
    p4input=$(find ${S} -name *.p4| head -n 1)
    p4file=$(basename -- ${p4input})
    p4name="${p4file%.*}"
    install -d ${D}${bindir}
    cp -r --preserve=mode ${B}/${prefix}/${p4name}.out ${D}${prefix}/
}

EXPORT_FUNCTIONS do_compile do_install 

