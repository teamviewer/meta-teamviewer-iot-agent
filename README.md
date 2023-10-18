meta-teamviewer-iot-agent
=========================

This layer provides support for including TeamViewer IoT Agent in Yocto Linux projects.

This layer uses TeamViewer IoT Agent v3.0.3.

Example on how the layer can be used is provided here https://community.teamviewer.com/t5/TeamViewer-IoT-Knowledge-Base/Building-a-Yocto-Image-for-Raspberry/ta-p/91297.

Please see the corresponding sections below for details.

**Maintainer: TeamViewer Germany GmbH (<https://www.teamviewer.com/en/support/contact>)**

Dependencies
------------
This layer depends on:

```
URI: git://git.openembedded.org/meta-openembedded
branch: kirkstone
```

Adding the meta-teamviewer-iot-agent layer and other dependencies to your build
========================================================

Run the following command to add the openembedded and teamviewer layers

bitbake-layers add-layer meta-openembedded/meta-filesystems/ \
	meta-openembedded/meta-oe/ \
	meta-openembedded/meta-gnome/ \
	meta-openembedded/meta-initramfs/ \
	meta-openembedded/meta-multimedia/ \
	meta-openembedded/meta-networking/ \
	meta-openembedded/meta-perl/ \
	meta-openembedded/meta-python/ \
	meta-openembedded/meta-webserver/ \
	meta-openembedded/meta-xfce/  \
	meta-teamviewer-iot-agent

Notes
=====

Add 'IMAGE_INSTALL:append = " teamviewer-iot-agent"' line in local.conf configuration file to append teamviewer-iot-agent layer to image.

Add 'DISTRO_FEATURES:append = " X11"' line in local.conf configuration file for installation X11 dependencies.

Add 'VOLATILE_LOG_DIR = "no"' line in local.conf configuration file to make it possible for the agent to write logfiles. 

Add 'IMAGE_ROOTFS_EXTRA_SPACE = "256000"' line in local.conf configuration file to make sure enough space is available on runtime for the agent

