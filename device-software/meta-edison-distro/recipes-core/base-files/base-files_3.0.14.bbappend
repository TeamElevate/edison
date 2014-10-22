FILESEXTRAPATHS_prepend := "${THISDIR}/base-files:"
SRC_URI += "file://fstab"
SRC_URI += "file://media-sdcard.mount"
SRC_URI += "file://factory.mount"

# override default volatile to suppress var/log link creation
volatiles = "tmp"

do_install_append() {
	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab

	# enable mount of the SDCard in /media/sdcard when inserted
	install -d ${D}${systemd_unitdir}/system
	install -c -m 0644 ${WORKDIR}/media-sdcard.mount ${D}${systemd_unitdir}/system
	install -c -m 0644 ${WORKDIR}/factory.mount ${D}${systemd_unitdir}/system
	# Enable the service
	install -d ${D}${sysconfdir}/systemd/system/default.target.wants
	ln -sf ${systemd_unitdir}/system/media-sdcard.mount \
		${D}${sysconfdir}/systemd/system/default.target.wants/media-sdcard.mount
    ln -sf ${systemd_unitdir}/system/factory.mount \
		${D}${sysconfdir}/systemd/system/default.target.wants/factory.mount

}

FILES_${PN} += "${base_libdir}/systemd/system/*.mount"
FILES_${PN} += "${sysconfdir}/systemd/system/default.target.wants/*.mount"
