/*
 * @(#)Ogg.java
 *
 * This file [was taken from] webCDwriter - Network CD Writing.
 *
 * Copyright (C) 2003 Jörg P. M. Haeger
 *
 * webCDwriter is free software under the GNU GPL.
 * Please see this page for more details: http://joerghaeger.de/webCDwriter/
 *
 * Slight modifications for external utility usage by Korynkai.
 */

package com.qmxtech.mccodeclib;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Class to get the length of ogg files.
 *
 * @version 20030220
 * @author Jörg P. M. Haeger
 * Added to MCCodecLib by Korynkai 20141217
 */

public class Ogg {
int audio_channels;
int audio_sample_rate;
long dataLength;
long headerStart;
long sampleNum;
int vorbis_version;

public Ogg(File file) throws Exception {
        this(file.getPath());
}

public Ogg(String file) throws Exception {
        dataLength = new File(file).length();
        FileInputStream inStream = new FileInputStream(file);

        int pos = 0;
        while (true) {
                int packet_type = 0;
                char[] capture_pattern1 = { 'v','o','r','b','i','s' };
                for (int i = 0; i < capture_pattern1.length; i++) {
                        int b = inStream.read();
                        if (b == -1)
                                throw new Exception(
                                              "no Vorbis identification header");
                        pos++;
                        if (b != capture_pattern1[i]) {
                                packet_type = b;
                                i = -1;
                        }
                }

                if (packet_type == 1)
                        break;
        }

        vorbis_version = read32Bits(inStream);
        if (vorbis_version > 0)
                throw new Exception("unknown vorbis_version "
                                    + vorbis_version);
        audio_channels = inStream.read();
        audio_sample_rate = read32Bits(inStream);
        pos += 4 + 1 + 4;

        headerStart = dataLength - 16 * 1024;
        inStream.skip(headerStart - pos);
        int count = 0;
        while (true) {
                char[] capture_pattern = { 'O', 'g', 'g', 'S', 0 };
                int i;
                for (i = 0; i < capture_pattern.length; i++) {
                        int b = inStream.read();
                        if (b == -1)
                                break;
                        if (b != capture_pattern[i]) {
                                headerStart += i + 1;
                                i = -1;
                        }
                }
                if (i < capture_pattern.length)
                        break;

                count++;

                int header_type_flag = inStream.read();
                if (header_type_flag == -1)
                        break;

                long absolute_granule_position = 0;
                for (i = 0; i < 8; i++) {
                        long b = inStream.read();
                        if (b == -1)
                                break;

                        absolute_granule_position |= b << (8 * i);
                }
                if (i < 8)
                        break;

                if ((header_type_flag & 0x06) != 0) {
                        sampleNum = absolute_granule_position;
                }
        }
}

public long getSeconds() {
        if (audio_sample_rate > 0)
                return sampleNum / audio_sample_rate;
        else
                return 0;
}

public static long getSeconds(String args) throws Exception {
        return (new Ogg(args)).getSeconds();
}

public static long getSeconds(String[] args) throws Exception {
        return (new Ogg(args[0])).getSeconds();
}

public static void main(String args) throws Exception {
        new Ogg(args).showInfo();
}

public static void main(String[] args) throws Exception {
        new Ogg(args[0]).showInfo();
}

public int read32Bits(InputStream inStream) throws Exception {
        int n = 0;
        for (int i = 0; i < 4; i++) {
                int b = inStream.read();
                if (b == -1)
                        throw new Exception("Unexpected end of input stream");
                n |= b << (8 * i);
        }
        return n;
}

public void showInfo() {
        System.out.println("audio_channels = " + audio_channels);
        System.out.println("audio_sample_rate = " + audio_sample_rate);
        System.out.println("dataLength = " + dataLength);
        System.out.println("seconds = " + getSeconds());
        System.out.println("headerStart = " + headerStart);
        System.out.println("vorbis_version = " + vorbis_version);
}
}
