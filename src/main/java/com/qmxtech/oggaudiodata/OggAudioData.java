/*
 * OggAudioData.java
 *
 * This file was based on Ogg.java from webCDwriter - Network CD Writing.
 *
 * Copyright (C) 2003 Jörg P. M. Haeger
 *
 * webCDwriter is free software under the GNU GPL.
 * Please see this page for more details: http://joerghaeger.de/webCDwriter/
 *
 * Refactorization for external library usage by Korynkai
 */

package com.qmxtech.oggaudiodata;

import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.io.InputStream;
import java.lang.Byte;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class to get the length of ogg files.
 *
 * @version 20030220
 * @author Jörg P. M. Haeger
 * Added to OggAudioData by Korynkai 20141217
 */

public class OggAudioData {

        // Public methods

        public static long getSeconds(InputStream stream, Long len) throws Exception {
                return (new OggAudioData(stream, len)).getSeconds();
        }

        public static long getSeconds(InputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSeconds();
        }

        public static long getSeconds(ZipInputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSeconds();
        }

        public static long getSeconds(String path) throws Exception {
                return (new OggAudioData(path)).getSeconds();
        }

        public static long getSeconds(File file) throws Exception {
                return (new OggAudioData(file)).getSeconds();
        }

        public static int getChannels(InputStream stream, Long len) throws Exception {
                return (new OggAudioData(stream, len)).getChannels();
        }

        public static int getChannels(InputStream stream) throws Exception {
                return (new OggAudioData(stream)).getChannels();
        }

        public static int getChannels(ZipInputStream stream) throws Exception {
                return (new OggAudioData(stream)).getChannels();
        }

        public static int getChannels(String path) throws Exception {
                return (new OggAudioData(path)).getChannels();
        }

        public static int getChannels(File file) throws Exception {
                return (new OggAudioData(file)).getChannels();
        }

        public static int getSampleRate(InputStream stream, Long len) throws Exception {
                return (new OggAudioData(stream, len)).getSampleRate();
        }

        public static int getSampleRate(InputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSampleRate();
        }

        public static int getSampleRate(ZipInputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSampleRate();
        }

        public static int getSampleRate(String path) throws Exception {
                return (new OggAudioData(path)).getSampleRate();
        }

        public static int getSampleRate(File file) throws Exception {
                return (new OggAudioData(file)).getSampleRate();
        }

        public static int getVorbisVersion(InputStream stream, Long len) throws Exception {
                return (new OggAudioData(stream, len)).getVorbisVersion();
        }

        public static int getVorbisVersion(InputStream stream) throws Exception {
                return (new OggAudioData(stream)).getVorbisVersion();
        }

        public static int getVorbisVersion(ZipInputStream stream) throws Exception {
                return (new OggAudioData(stream)).getVorbisVersion();
        }

        public static int getVorbisVersion(String path) throws Exception {
                return (new OggAudioData(path)).getVorbisVersion();
        }

        public static int getVorbisVersion(File file) throws Exception {
                return (new OggAudioData(file)).getVorbisVersion();
        }

        // Virtually useless... Size already known at time of call... Implemented anyway for API uniformity.
        public static long getSizeInBytes(InputStream stream, Long len) throws Exception {
                return (new OggAudioData(stream, len)).getSizeInBytes();
        }

        public static long getSizeInBytes(InputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSizeInBytes();
        }

        public static long getSizeInBytes(ZipInputStream stream) throws Exception {
                return (new OggAudioData(stream)).getSizeInBytes();
        }

        public static long getSizeInBytes(String path) throws Exception {
                return (new OggAudioData(path)).getSizeInBytes();
        }

        // Virtually useless... Size already known at time of call... Implemented anyway for API uniformity.
        public static long getSizeInBytes(File file) throws Exception {
                return (new OggAudioData(file)).getSizeInBytes();
        }

        public static void showInfo(InputStream stream, Long len) throws Exception {
                new OggAudioData(stream, len).showInfo();
        }

        public static void showInfo(InputStream stream) throws Exception {
                new OggAudioData(stream).showInfo();
        }

        public static void showInfo(ZipInputStream stream) throws Exception {
                new OggAudioData(stream).showInfo();
        }

        public static void showInfo(String path) throws Exception {
                new OggAudioData(path).showInfo();
        }

        public static void showInfo(File file) throws Exception {
                new OggAudioData(file).showInfo();
        }

        // Private Constructors

        private OggAudioData(File file) throws Exception {
                initValues((InputStream) (new FileInputStream(file)), file.length());
        }

        private OggAudioData(String file) throws Exception {
                initValues((InputStream) (new FileInputStream(file)), (new File(file)).length());
        }

        private OggAudioData(InputStream is) throws Exception {
                PushbackInputStream stream = new PushbackInputStream(is, 20000000);
                long size = 0;
                int x = 0;
                byte bt = 0;
                ArrayList<Byte> btList = new ArrayList<Byte>();

                while (bt != -1) {
                        x = stream.read();
                        bt = (byte) x;
                        if (bt == -1) {
                                break;
                        }
                        size++;
                        btList.add(bt);
                }

                byte[] reset = new byte[(int)size];
                int j=0;
                for(Byte y: btList) {
                        reset[j++] = y.byteValue();
                }

                stream.unread(reset);
                initValues(stream, size);
        }

        private OggAudioData(InputStream stream, Long len) throws Exception {
                initValues(stream, len);
        }

        private OggAudioData(ZipInputStream stream) throws Exception {
                ZipEntry entry;
                if ((entry = stream.getNextEntry()) != null) {
                        initValues(stream, entry.getSize());
                }
        }

        // Private methods

        private void initValues(InputStream stream, Long len) throws Exception {
                dataLength = len;
                int pos = 0;
                while (true) {
                        int packet_type = 0;
                        char[] capture_pattern1 = { 'v','o','r','b','i','s' };
                        for (int i = 0; i < capture_pattern1.length; i++) {
                                int b = stream.read();
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

                vorbis_version = read32Bits(stream);
                if (vorbis_version > 0)
                        throw new Exception("unknown vorbis_version "
                                            + vorbis_version);
                audio_channels = stream.read();
                audio_sample_rate = read32Bits(stream);
                pos += 4 + 1 + 4;

                headerStart = dataLength - 16 * 1024;
                stream.skip(headerStart - pos);
                int count = 0;
                while (true) {
                        char[] capture_pattern = { 'O', 'g', 'g', 'S', 0 };
                        int i;
                        for (i = 0; i < capture_pattern.length; i++) {
                                int b = stream.read();
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

                        int header_type_flag = stream.read();
                        if (header_type_flag == -1)
                                break;

                        long absolute_granule_position = 0;
                        for (i = 0; i < 8; i++) {
                                long b = stream.read();
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

        private long getSeconds() {
                if (audio_sample_rate > 0)
                        return sampleNum / audio_sample_rate;
                else
                        return 0;
        }

        private int getChannels() {
                if (audio_sample_rate > 0)
                        return audio_sample_rate;
                else
                        return 0;
        }

        private int getSampleRate() {
                if (audio_channels > 0)
                        return audio_channels;
                else
                        return 0;
        }

        private int getVorbisVersion() {
                if (vorbis_version > 0)
                        return vorbis_version;
                else
                        return 0;
        }

        private long getSizeInBytes() {
                if (dataLength > 0)
                        return dataLength;
                else
                        return 0;
        }

        private int read32Bits(InputStream inStream) throws Exception {
                int n = 0;
                for (int i = 0; i < 4; i++) {
                        int b = inStream.read();
                        if (b == -1)
                                throw new Exception("Unexpected end of input stream");
                        n |= b << (8 * i);
                }
                return n;
        }

        private void showInfo() {
                System.out.println("audio_channels = " + audio_channels);
                System.out.println("audio_sample_rate = " + audio_sample_rate);
                System.out.println("dataLength = " + dataLength);
                System.out.println("seconds = " + getSeconds());
                System.out.println("headerStart = " + headerStart);
                System.out.println("vorbis_version = " + vorbis_version);
        }

        // Private fields

        private int audio_channels;
        private int audio_sample_rate;
        private long dataLength;
        private long headerStart;
        private long sampleNum;
        private int vorbis_version;

}
