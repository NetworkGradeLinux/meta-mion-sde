# SPDX-License-Identifier: MIT

SUMMARY = "Barefoot SDE"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = ' \
    packagegroup-sde-barefoot \
'

RDEPENDS_packagegroup-sde-barefoot = "\
  barefoot-bsp grpc bf-python bf-utils bf-syslibs bf-drivers procps bf-kdrv boost pi judy \
"

EXCLUDE_FROM_WORLD = "1"
