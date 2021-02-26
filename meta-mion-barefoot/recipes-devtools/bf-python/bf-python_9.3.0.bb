SUMMARY = "Patched version of Python 3 distributed with the Barefoot SDE"
HOMEPAGE = "http://www.python.org"
LICENSE = "PSFv2"

DEPENDS = "bf-python-native libffi bzip2 db gdbm openssl readline sqlite3 zlib virtual/libintl xz"
SRC_URI = "git://${MIONBASE}/bf-sde; \
           file://python-config.patch \
           file://000-cross-compile.patch \
           file://020-dont-compile-python-files.patch \
           file://030-fixup-include-dirs.patch \
           file://070-dont-clean-ipkg-install.patch \
           file://080-distutils-dont_adjust_files.patch \
           file://110-enable-zlib.patch \
           file://130-readline-setup.patch \
           file://150-fix-setupterm.patch \
           file://0001-h2py-Fix-issue-13032-where-it-fails-with-UnicodeDeco.patch \
           file://03-fix-tkinter-detection.patch \
           file://04-default-is-optimized.patch \
           file://avoid_warning_about_tkinter.patch \
           file://cgi_py.patch \
           file://host_include_contamination.patch \
           file://python-3.3-multilib.patch \
           file://shutil-follow-symlink-fix.patch \
           file://sysroot-include-headers.patch \
           file://unixccompiler.patch \
           file://avoid-ncursesw-include-path.patch \
           file://python3-use-CROSSPYTHONPATH-for-PYTHON_FOR_BUILD.patch \
           file://python3-setup.py-no-host-headers-libs.patch \
           file://sysconfig.py-add-_PYTHON_PROJECT_SRC.patch \
           file://setup.py-check-cross_compiling-when-get-FLAGS.patch \
           file://setup.py-find-libraries-in-staging-dirs.patch \
           file://use_packed_importlib.patch \
           file://0001-Update-shebang-to-python3.patch \
           "
SRCREV="${AUTOREV}"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a4f23ab61ee8baf13a46b19c5cb2226b"

S = "${WORKDIR}/git/bf-sde-${PV}/pkgsrc/bf-utils/third-party/${BPN}"

PKG_DIR = "${WORKDIR}/git/"

FILES_${PN} += "${libdir}/python3.4/*"
FILES_${PN}-staticdev += "${libdir}/python3.4/*/*.a"

INSANE_SKIP_${PN} += " file-rdeps"
INSANE_SKIP_${PN}-dev += "dev-elf"

inherit autotools multilib_header pkgconfig

CACHED_CONFIGUREVARS = "ac_cv_have_chflags=no \
                ac_cv_have_lchflags=no \
                ac_cv_have_long_long_format=yes \
                ac_cv_buggy_getaddrinfo=no \
                ac_cv_file__dev_ptmx=yes \
                ac_cv_file__dev_ptc=no \
"

EXTRA_OEMAKE = '\
	HOSTPGEN=${STAGING_BINDIR_NATIVE}/pgen \
 	BUILD_SYS="" \
    HOST_SYS="" \
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
  	install -m 0755 python ${D}${bindir}/python3.4m
    # Remove libpython3.so, it clashes with the standard Python
    rm ${D}${libdir}/libpython3.so
}
