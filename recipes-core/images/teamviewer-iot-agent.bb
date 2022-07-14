
DESCRIPTION = "TeamViewer IoT Agent Into Yocto For RaspberryPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"
SRC_URI += "file://LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"

TEAMVIEWER_IOT_AGENT_VERSION="2.22.2"
SRC_URI += "https://download-iot.teamviewer.com/agents/linux/${TEAMVIEWER_IOT_AGENT_VERSION}/aarch64/teamviewer-iot-agent_${TEAMVIEWER_IOT_AGENT_VERSION}_arm64.deb"
SRC_URI[md5sum] = "f52cd55249eef431fd75922340861d11"
SRC_URI[sha256sum] = "e6949df49b2bd3d8d6c0dd6cba8b11be910393d35931074a1fb7b15565f16322"

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
	ar x ${DL_DIR}/teamviewer-iot-agent-armv7_${TEAMVIEWER_IOT_AGENT_VERSION}_armhf.deb
	tar xf control.tar.gz
	install -m 0700 preinst ${D}/usr/share/teamviewer-iot-agent/
	install -m 0700 postinst ${D}/usr/share/teamviewer-iot-agent/
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
