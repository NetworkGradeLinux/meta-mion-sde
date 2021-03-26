# SPDX-License-Identifier: MIT

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "A Python DSL for defining JSON schemas"
HOMEPAGE = "https://jsl.readthedocs.io"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=557d05079e624a0685edc1532fec6c3f"

SRC_URI = "git://github.com/aromanovich/jsl;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "1b0cb1d4b0c28993dd29a7156fd0aa63a25c5030"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native"

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.
RDEPENDS_${PN} += "python3-core python3-netclient"

# WARNING: We were unable to map the following python package/module
# dependencies to the bitbake packages which include them:
#    UserDict
#    urlparse
