meta-teamviewer-iot-agent
=========================

This layer provides support for including TeamViewer IoT Agent in Yocto Linux projects.

This layer uses TeamViewer IoT Agent v2.10.

Example on how the layer can be used is provided here https://community.teamviewer.com/t5/TeamViewer-IoT-Knowledge-Base/Building-a-Yocto-Image-for-Raspberry/ta-p/91297.

Please see the corresponding sections below for details.

**Maintainer: TeamViewer Germany GmbH (<https://www.teamviewer.com/en/support/contact>)**

Dependencies
------------
This layer depends on:

```
URI: git://git.yoctoproject.org/meta-virtualization
branch: dunfell
```

```
URI: git://git.openembedded.org/meta-openembedded
branch: dunfell
```

Adding the meta-teamviewer-iot-agent layer to your build
========================================================

Run `bitbake-layers add-layer meta-teamviewer-iot-agent`

Notes
=====

Add 'IMAGE_INSTALL_append += " teamviewer-iot-agent"' line in local.conf configuration file to append teamviewer-iot-agent layer to image.

Add 'DISTRO_FEATURES_append += " X11"' line in local.conf configuration file for installation X11 dependencies.

Add 'DISTRO_FEATURES_append += " virtualization"' line in local.conf configuration file for docker installation.
