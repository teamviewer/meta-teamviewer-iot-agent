SUMMARY = "TeamViewer IoT Agent provides easy, fast and secure remote access to your device."
DESCRIPTION = "The TeamViewer IoT agent provides an out-of-the-box \
secure remote access and monitoring solution for your IoT environment \
and connected devices. . The agent seamlessly integrates with the \
latest TeamViewer client, which enables you to remotely access your \
device & remotely view live monitoring data. The Teamviewer Client can \
be downloaded at http://www.teamviewer.com/. . TeamViewer IoT Agent is \
currently available for free trial. As licensing may be subject to \
change, please visit https://teamviewer-iot.com. . Notes . Use of the \
TeamViewer IoT Agent must adhere to the end user license agreement. \
Reference http://www.teamviewer.com/link/?url=653670 . TeamViewer IoT \
Agent contains Free Software components. Reference \
/usr/share/doc/teamviewer-iot-agent/Third_Party_License.txt"
HOMEPAGE = "http://www.teamviewer.com"
SECTION = "non-free/misc"

LICENSE_FLAGS = "commercial"
LICENSE_FLAGS_DETAILS:${PN} = "For further details, see https://www.teamviewer.com/link/?url=418720" 

LIC_FILES_CHKSUM = "file://../copyright;md5=098680d7bb2ef92ca3cec0b546683658;\
	file://../Third_Party_License_IoT.txt;md5=d8029066642f39a3dcfa05a87651015c;"
	
SRC_URI += "file://copyright;md5=098680d7bb2ef92ca3cec0b546683658"
SRC_URI += "file://Third_Party_License_IoT.txt;md5=d8029066642f39a3dcfa05a87651015c"

LICENSE = "copyright & Third_Party_License_IoT.txt"

RDEPENDS:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'X11', ' xserver-xorg-xvfb xserver-xorg xkeyboard-config xauth', '', d)}"

RDEPENDS:${PN} += "bash perl glibc glibc-utils procps curl ca-certificates"
RRECOMMENDS:${PN} += "dbus libavahi-core libavahi-common libavahi-client"

TEAMVIEWER_IOT_AGENT_VERSION="3.5.10"

SRC_URI += "https://download-iot.teamviewer.com/agents/linux/${TEAMVIEWER_IOT_AGENT_VERSION}/armv7/teamviewer-iot-agent-armv7_${TEAMVIEWER_IOT_AGENT_VERSION}_armhf.deb"
SRC_URI[md5sum] = "9909065535228ee68db27a0c9ad7ef09"
SRC_URI[sha1sum] = "5d90c6c42d1a34f43680eb13a3fc049c9bc877db"
SRC_URI[sha256sum] = "670dc40794e3ae39664e3c6ab19d443b4e86d349fc6634957397535f5c4ed944"
SRC_URI[sha384sum] = "3de1fd315e13eb9f8cdea1f752ea39ae51f4e3512486449c2376c2061c39dd6524cb2c09d9373b5465967b28eb539ac9"
SRC_URI[sha512sum] = "23dd1ef2fc29179b85364109335ad0fb60b27407aeff01a6c665624fe73ba163c85b1e0e74a5722c6d8585f3efd85511a34ebde8e3d79f7f2e536bd7c96b23b7"

# NOTE: no Makefile found, unable to determine what needs to be done

do_configure () {
	# Specify any needed configure commands here
	:
}

do_compile () {
	# Specify compilation commands here
	:
}

do_install () {
    #TeamViewer IoT Agent
	cp -r ${WORKDIR}/etc ${D}/etc
	cp -r ${WORKDIR}/lib ${D}/lib
	cp -r ${WORKDIR}/usr ${D}/usr
	cp -r ${WORKDIR}/var ${D}/var
	cp -r ${WORKDIR}/usr/share/doc ${D}/usr/share/teamviewer-iot-agent-layer-docs

	#Installation scripts
	ar x ${DL_DIR}/teamviewer-iot-agent-armv7_${TEAMVIEWER_IOT_AGENT_VERSION}_armhf.deb
	tar xf control.tar.gz --no-same-owner
	install -m 0700 preinst ${D}/usr/share/teamviewer-iot-agent/
	install -m 0700 postinst ${D}/usr/share/teamviewer-iot-agent/
    install -d ${D}/var/log/teamviewer-iot-agent/
    install -d ${D}/etc/default/
}

pkg_postinst_ontarget:${PN} () {
    mkdir -p /usr/share/doc /var/log/teamviewer-iot-agent
    mv /usr/share/teamviewer-iot-agent-layer-docs/* /usr/share/doc/
    /usr/share/teamviewer-iot-agent/preinst
    /usr/share/teamviewer-iot-agent/postinst
    rm -f /usr/share/teamviewer-iot-agent/preinst
    rm -f /usr/share/teamviewer-iot-agent-layer-docs
}

FILES:${PN} += "/etc/* \
		/var/* \
		/lib/* \
		/usr/*"

INSANE_SKIP:${PN} += "already-stripped ldflags"
