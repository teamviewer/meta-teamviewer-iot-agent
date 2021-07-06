
DESCRIPTION = "TeamViewer IoT Agent Into Yocto For RaspberryPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"
SRC_URI += "file://LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"

TEAMVIEWER_IOT_AGENT_DEB="teamviewer-iot-agent_2.16.952_aarch64.deb"
SRC_URI += "https://download.teamviewer-iot.com/agents/2.16.952/aarch64/${TEAMVIEWER_IOT_AGENT_DEB}"
SRC_URI[md5sum] = "a380b66bd7aa209d71cde05b096ae394"
SRC_URI[sha256sum] = "ef61db83e5b821d61fe2cc0c5b9214a2552c49c414717170044811d44d508d41"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', ' docker-ce kernel-modules lsof', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'X11', ' xserver-xorg-xvfb xserver-xorg xkeyboard-config xauth', '', d)}"
RDEPENDS_${PN} += "bash perl dbus glibc glibc-utils libavahi-core libavahi-common libavahi-client procps curl ca-certificates"

do_install() {
	#TeamViewer IoT Agent
	cp -r ${WORKDIR}/etc ${D}/etc
	cp -r ${WORKDIR}/lib ${D}/lib
	cp -r ${WORKDIR}/usr ${D}/usr
	cp -r ${WORKDIR}/var ${D}/var
	cp -r ${WORKDIR}/usr/share/doc ${D}/usr/share/teamviewer-iot-agent-layer-docs

	#Installation scripts
	ar x ${DL_DIR}/${TEAMVIEWER_IOT_AGENT_DEB}
	/usr/bin/tar xf control.tar.gz --directory /tmp
	install -m 0700 /tmp/preinst ${D}/usr/share/teamviewer-iot-agent/
	install -m 0700 /tmp/postinst ${D}/usr/share/teamviewer-iot-agent/
}

do_install_append () {
	rm -f ${D}${libdir}/ld-linux-aarch64.so.1
}

FILES_${PN} += "/etc \
		/var \
		/lib \
		/usr"

pkg_postinst_ontarget_${PN} () {
		mkdir -p /usr/share/doc /var/log/teamviewer-iot-agent
		mv /usr/share/teamviewer-iot-agent-layer-docs/* /usr/share/doc/
		/usr/share/teamviewer-iot-agent/preinst
		/usr/share/teamviewer-iot-agent/postinst
		rm -f /usr/share/teamviewer-iot-agent/preinst
		rm -f /usr/share/teamviewer-iot-agent-layer-docs
}

INSANE_SKIP_${PN} += "already-stripped ldflags"
