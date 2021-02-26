LICENSE = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f3453ba8e98aaed11a290758a999e65 \
                    file://proto/p4runtime/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://proto/openconfig/gnmi/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://third_party/googletest/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"

SRCREV_pi = "4546038f5770e84dc0d2bba90f1ee7811c9955df"
SRCREV_p4runtime = "c09cd0bf2b529d328c7325ab00ab013ea74dc796"
SRCREV_gnmi = "9c8d9e965b3e854107ea02c12ab11b70717456f2"
SRCREV_googletest = "f5edb4f542e155c75bc4b516f227911d99ec167c"

SRC_URI = "git://github.com/p4lang/PI;name=${BPN} \
           git://github.com/p4lang/p4runtime;branch=main;name=p4runtime;destsuffix=git/proto/p4runtime \
           git://github.com/openconfig/gnmi.git;name=gnmi;destsuffix=git/proto/openconfig/gnmi \
           git://github.com/google/googletest.git;name=googletest;destsuffix=git/third_party/googletest \
           file://0001-Avoid-host-includes-when-cross-compiling.patch \
           file://0001-Relax-file-existence-tests-to-allow-cross-compile.patch \
          "

FILES_${PN} += "${libdir}/python3.8/*"

EXTRA_OECONF += "--with-proto=yes --without-bmv2 --without-internal-rpc --without-cli --with-boost-libdir=${STAGING_LIBDIR}"

DEPENDS = "judy protobuf protobuf-native grpc grpc-native boost python3-native"

S = "${WORKDIR}/git"

inherit autotools-brokensep python3native python3-dir 
