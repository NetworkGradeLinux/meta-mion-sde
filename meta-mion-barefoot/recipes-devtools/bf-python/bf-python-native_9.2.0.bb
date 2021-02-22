SUMMARY = "Patched version of Python 3 distributed with the Barefoot SDE"
HOMEPAGE = "http://www.python.org"
LICENSE = "PSFv2"

SRC_URI = "git://${MIONBASE}/bf-sde; \
file://12-distutils-prefix-is-inside-staging-area.patch \
file://python-config.patch \
file://000-cross-compile.patch \
file://020-dont-compile-python-files.patch \
file://030-fixup-include-dirs.patch \
file://070-dont-clean-ipkg-install.patch \
file://080-distutils-dont_adjust_files.patch \
file://110-enable-zlib.patch \
file://130-readline-setup.patch \
file://150-fix-setupterm.patch \
file://python-3.3-multilib.patch \
file://03-fix-tkinter-detection.patch \
file://avoid_warning_about_tkinter.patch \
file://shutil-follow-symlink-fix.patch \
file://0001-h2py-Fix-issue-13032-where-it-fails-with-UnicodeDeco.patch \
file://sysroot-include-headers.patch \
file://unixccompiler.patch \
file://sysconfig.py-add-_PYTHON_PROJECT_SRC.patch \
file://setup.py-check-cross_compiling-when-get-FLAGS.patch \
file://0001-Update-shebang-to-python3.patch \
"

SRCREV="${AUTOREV}"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a4f23ab61ee8baf13a46b19c5cb2226b"

S = "${WORKDIR}/git/bf-sde-${PV}/pkgsrc/bf-utils/third-party/${BPN}"

PKG_DIR = "${WORKDIR}/git/"

DEPENDS = "openssl-native bzip2-replacement-native zlib-native readline-native sqlite3-native"

inherit autotools native

EXTRA_OEMAKE = '\
  BUILD_SYS="" \
  HOST_SYS="" \
  LIBC="" \
  STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE} \
  STAGING_INCDIR=${STAGING_INCDIR_NATIVE} \
  PYTHON=${PYTHON} \
'

extract() {
    # Extract SDE
    tar xzf ${PKG_DIR}/bf-sde-${PV}.tgz --directory ${PKG_DIR}
    # Extract package
    mkdir -p ${PKG_DIR}/bf-sde-${PV}/pkgsrc/bf-utils
    tar xzf ${PKG_DIR}/bf-sde-${PV}/packages/bf-utils-${PV}.tgz --directory ${PKG_DIR}/bf-sde-${PV}/pkgsrc/bf-utils --strip-components 1
}

python do_unpack_append() {
    bb.build.exec_func("extract", d)
}

do_install_append() {
	install -d ${D}${bindir}
	install -m 0755 Parser/pgen ${D}${bindir}
	install -m 0755 python ${D}${bindir}/python3
	# Remove libpython3.so, it clashes with the standard Python
	rm ${D}${libdir}/libpython3.so
}
