
DESCRIPTION = "TeamViewer IoT Agent Into Yocto For RaspberryPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"
SRC_URI += "file://LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"

TEAMVIEWER_IOT_AGENT_VERSION="2.10.18"
SRC_URI += "https://download.teamviewer-iot.com/agents/${TEAMVIEWER_IOT_AGENT_VERSION}/armv7/teamviewer-iot-agent-armv7_${TEAMVIEWER_IOT_AGENT_VERSION}_armhf.deb"
SRC_URI[md5sum] = "88215b31e9d26f8d5d9c1e9980ff219b"
SRC_URI[sha256sum] = "f6a5374517a90fa3dd25728dbc57de7038651bc8461270d64041c105d325ab74"

SRC_URI += "http://ftp.de.debian.org/debian/pool/main/d/dbus/libdbus-1-3_1.12.20-0+deb10u1_armhf.deb;md5sum=7a2ea0a2f824e0c177506eb70b60279e;sha256sum=3457e8bf06139c0892c78f4d727b39c3ee90f918b56a9eb765f07333c77b3cf0"
PRIVATE_LIBS_${PN} += " libdbus-1.so.3"

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
