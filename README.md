## OGG Audio Metadata Utility Library

This is a universal Java library designed to add limited OGG/Vorbis file metadata retrieval methods to any Java application.
This library is intended to be used alongside Numina's Musique proxy for Minecraft, though it may have other uses as well.

This library is licensed under the GNU GPL and is based on the OGG metadata handling class for webCDcreator (available at http://haeger.name/webCDwriter)

>Latest Builds: (currently unavailable)

#### Known Issues:

* None reported.

#### Recent Changes:
>2014-12-20 by Korynkai:

* Some more refactoring - Reorganized, removed redundant code. Also imposed programatic access strictness.
* Small fixes I didn't catch before with the "lazy" input stream constructor... Also added a test class and an ogg file to test with, Wikipedia's courtesy Median Test ogg file which is licensed under the GNU GPL.

>2014-12-19 by Korynkai:

* Added ZipInputStream as source for constructor and static methods.

>2014-12-18 by Korynkai:

* Refactored Ogg.java for better flexability (now renamed to OggAudioData.java).
* Renamed project to better reflect its purpose.

>2014-12-17 by Korynkai:

* Initial commit: Prepared and added Ogg.java from webCDcreator and initialized workspace.
 