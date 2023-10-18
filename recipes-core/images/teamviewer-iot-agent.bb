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

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"
SRC_URI += "file://LICENSE;md5=42d92b6e835edaca7b91d7007b64e737"


RDEPENDS:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'X11', ' xserver-xorg-xvfb xserver-xorg xkeyboard-config xauth', '', d)}"
RDEPENDS:${PN} += "bash perl glibc glibc-utils procps curl ca-certificates"
RRECOMMENDS:${PN} += "dbus libavahi-core libavahi-common libavahi-client"

TEAMVIEWER_IOT_AGENT_VERSION="3.0.3"

SRC_URI += "https://download-iot.teamviewer.com/agents/linux/${TEAMVIEWER_IOT_AGENT_VERSION}/armv7/teamviewer-iot-agent-armv7_${TEAMVIEWER_IOT_AGENT_VERSION}_armhf.deb"
SRC_URI[md5sum] = "d6a958aa7acc451eb4872eaf01600cce"
SRC_URI[sha1sum] = "cff3842ffb2b26fe873e86227ce6ae70e7dbca68"
SRC_URI[sha256sum] = "e39aff3580b4d528dec10bbe5052cefa7de31e797b607017092e615ba3de8b35"
SRC_URI[sha384sum] = "81e114ad448b9553e8c79ab17a09dcd36852f0be5efca77dc655b9eba215895f86fe644d5b0565d080d09233d04aac0d"
SRC_URI[sha512sum] = "0780438803c63938a2dac247dab205d4954b2e87a3b6949df89ca721dedc2da02502335c96d82c2eaa6a244c0c0a6297471ca69bbd504876cd2c68308329f5a0"

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
