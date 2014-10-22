DESCRIPTION = "Inter of Things communication library for device-to-device and device-to-cloud messaging"
LICENSE = "LGPLv2.1"

S = "${EDISONREPO_TOP_DIR}/mw/iecf-c"

LIC_FILES_CHKSUM = " \
        file://LICENSE;md5=1a6d268fd218675ffea8be556788b780 \
"

DEPENDS = "zeromq mdns paho-mqtt"

inherit pkgconfig cmake

do_configure() {
    cd ${B}/
    cmake ${S}/src
}

do_compile() {
    cd ${B}/
    oe_runmake
}

do_install() {
    # Copy the header files to /usr/include/iotkit-comm
    install -d ${D}${includedir}/iotkit-comm
    install -m 644 ${S}/src/lib/cJSON/cJSON.h ${D}${includedir}/iotkit-comm/
    install -m 644 ${S}/src/lib/libiotkit-comm/iotkit-comm.h ${D}${includedir}/iotkit-comm/
    install -m 644 ${S}/src/lib/libiotkit-comm/iotkit-comm_mdns.h ${D}${includedir}/iotkit-comm/
    install -m 644 ${S}/src/lib/libiotkit-comm/util.h ${D}${includedir}/iotkit-comm/

    # Copy the shared libraries to /usr/lib
    install -d ${D}${libdir}
    install ${B}/lib/libiotkit-comm/libiotkit-comm.so ${D}${libdir}/
    install ${B}/lib/plugins/libiotkitpubsub/libiotkit-agent-client.so ${D}${libdir}/
    install ${B}/lib/plugins/libmqttpubsub/libmqttpubsub-client.so ${D}${libdir}/
    install ${B}/lib/plugins/libzmqpubsub/libzmqpubsub-client.so ${D}${libdir}/
    install ${B}/lib/plugins/libzmqpubsub/libzmqpubsub-service.so ${D}${libdir}/
    install ${B}/lib/plugins/libzmqreqrep/libzmqreqrep-client.so ${D}${libdir}/
    install ${B}/lib/plugins/libzmqreqrep/libzmqreqrep-service.so ${D}${libdir}/

    # Copy config files
    install -d ${D}${sysconfdir}/iotkit-comm
    install -m 644 ${S}/src/lib/libiotkit-comm/config.json ${D}${sysconfdir}/iotkit-comm/
    cp -r ${S}/src/lib/libiotkit-comm/plugin-interfaces ${D}${sysconfdir}/iotkit-comm/

    # Copy the Sample apps
    install -d ${D}${datadir}/iotkit-comm
    install -d ${D}${datadir}/iotkit-comm/examples
    install -d ${D}${datadir}/iotkit-comm/examples/c
    cp -r ${S}/src/examples/iotkit-apps ${D}${datadir}/iotkit-comm/examples/c/
    cp -r ${S}/src/examples/zmq-apps ${D}${datadir}/iotkit-comm/examples/c/
    cp -r ${S}/src/examples/mqtt-apps ${D}${datadir}/iotkit-comm/examples/c/
    cp -r ${S}/src/examples/serviceQueries ${D}${datadir}/iotkit-comm/examples/c/
    cp -r ${S}/src/examples/serviceSpecs ${D}${datadir}/iotkit-comm/examples/c/
    cp -r ${S}/src/examples/distributed-thermostat ${D}${datadir}/iotkit-comm/examples/c/
    rm -rf ${D}${datadir}/iotkit-comm/examples/c/iotkit-apps/CMakeLists.txt
    rm -rf ${D}${datadir}/iotkit-comm/examples/c/mqtt-apps/CMakeLists.txt
    rm -rf ${D}${datadir}/iotkit-comm/examples/c/zmq-apps/CMakeLists.txt
    rm -rf ${D}${datadir}/iotkit-comm/examples/c/distributed-thermostat/CMakeLists.txt
    rm -rf ${D}${datadir}/iotkit-comm/examples/c/distributed-thermostat/doxygen.conf
    install ${B}/examples/distributed-thermostat/dashboard ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/distributed-thermostat/sensor ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/distributed-thermostat/thermostat ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/iotkit-apps/iotkitclient ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/iotkit-apps/iotkitservice ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/mqtt-apps/subscriber ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/mqtt-apps/publisher ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/zmq-apps/zmqsubclient ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/zmq-apps/zmqpubservice ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/zmq-apps/zmqreqclient ${D}${datadir}/iotkit-comm/examples/c/
    install ${B}/examples/zmq-apps/zmqrepservice ${D}${datadir}/iotkit-comm/examples/c/

    # Copy the invalid/valid config json files which are needed for Test programs
    install -d ${D}${datadir}/iotkit-comm/tests/
    install -d ${D}${datadir}/iotkit-comm/tests/c/
    install -d ${D}${datadir}/iotkit-comm/tests/c/libiotkit-comm
    cp ${S}/src/tests/libiotkit-comm/*.json ${D}${datadir}/iotkit-comm/tests/c/libiotkit-comm/

    # Copy test programs
    cp ${B}/tests/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/
    install -d ${D}${datadir}/iotkit-comm/tests/c/iotkitpubsub
    cp ${B}/tests/iotkitpubsub/iotkitpubsub* ${D}${datadir}/iotkit-comm/tests/c/iotkitpubsub/
    cp ${B}/tests/iotkitpubsub/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/iotkitpubsub/
    install -d ${D}${datadir}/iotkit-comm/tests/c/mqttpubsub
    cp ${B}/tests/mqttpubsub/mqttpubsub* ${D}${datadir}/iotkit-comm/tests/c/mqttpubsub/
    cp ${B}/tests/mqttpubsub/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/mqttpubsub/
    install -d ${D}${datadir}/iotkit-comm/tests/c/zmqpubsub
    cp ${B}/tests/zmqpubsub/zmqpubsub_* ${D}${datadir}/iotkit-comm/tests/c/zmqpubsub/
    cp ${B}/tests/zmqpubsub/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/zmqpubsub/
    cp ${B}/tests/libiotkit-comm/mdns_* ${D}${datadir}/iotkit-comm/tests/c/libiotkit-comm/
    cp ${B}/tests/libiotkit-comm/iotkit-comm_* ${D}${datadir}/iotkit-comm/tests/c/libiotkit-comm/
    cp ${B}/tests/libiotkit-comm/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/libiotkit-comm/
    install -d ${D}${datadir}/iotkit-comm/tests/c/zmqreqrep
    cp ${B}/tests/zmqreqrep/zmqreqrep_* ${D}${datadir}/iotkit-comm/tests/c/zmqreqrep/
    cp ${B}/tests/zmqreqrep/CTestTestfile.cmake ${D}${datadir}/iotkit-comm/tests/c/zmqreqrep/
}

FILES_${PN} += "${libdir}"
RDEPENDS_${PN} = "zeromq mdns paho-mqtt rsmb"

PACKAGES += "${PN}-tests"
RDEPENDS_${PN}-tests += "${PN} gcov"

FILES_${PN}-dev = "${includedir} ${datadir}/iotkit-comm/examples/c/"
FILES_${PN}-dbg += "${datadir}/iotkit-comm/examples/c/.debug/ ${datadir}/iotkit-comm/tests/c/iotkitpubsub/.debug/ ${datadir}/iotkit-comm/tests/c/mqttpubsub/.debug/ ${datadir}/iotkit-comm/tests/c/zmqpubsub/.debug/ ${datadir}/iotkit-comm/tests/c/libiotkit-comm/.debug/ ${datadir}/iotkit-comm/tests/c/zmqreqrep/.debug/"
FILES_${PN}-tests += "${datadir}/iotkit-comm/tests/c/"
