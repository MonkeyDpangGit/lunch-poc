package com.test.service.freetts;

import com.sun.speech.freetts.audio.AudioPlayer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * SimpleOutputAudioPlayer
 *
 * @author torrisli
 * @date 2025/4/21
 * @Description: SimpleOutputAudioPlayer
 */
public class SimpleOutputAudioPlayer implements AudioPlayer {

    private AudioFormat currentFormat;
    private byte[] outputData;
    private byte[] fullOutputData;
    private int curIndex;
    private int totBytes;
    private AudioFileFormat.Type outputType;
    private Vector outputList;

    public SimpleOutputAudioPlayer(AudioFileFormat.Type type) {
        this.currentFormat = null;
        this.curIndex = 0;
        this.totBytes = 0;
        this.outputType = type;
        this.outputList = new Vector();
    }

    public synchronized void setAudioFormat(AudioFormat format) {
        this.currentFormat = format;
    }

    public AudioFormat getAudioFormat() {
        return this.currentFormat;
    }

    public void pause() {
    }

    public synchronized void resume() {
    }

    public synchronized void cancel() {
    }

    public synchronized void reset() {
    }

    public void startFirstSampleTimer() {
    }

    public synchronized void close() {

        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = new SequenceInputStream(this.outputList.elements());
            AudioInputStream ais = new AudioInputStream(is, this.currentFormat,
                    (long) (this.totBytes / this.currentFormat.getFrameSize()));

            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            fullOutputData = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public float getVolume() {
        return 1.0F;
    }

    public void setVolume(float volume) {
    }

    public void begin(int size) {
        this.outputData = new byte[size];
        this.curIndex = 0;
    }

    public boolean end() {
        this.outputList.add(new ByteArrayInputStream(this.outputData));
        this.totBytes += this.outputData.length;
        return true;
    }

    public boolean drain() {
        return true;
    }

    public synchronized long getTime() {
        return -1L;
    }

    public synchronized void resetTime() {
    }

    public boolean write(byte[] audioData) {
        return this.write(audioData, 0, audioData.length);
    }

    public boolean write(byte[] bytes, int offset, int size) {
        System.arraycopy(bytes, offset, this.outputData, this.curIndex, size);
        this.curIndex += size;
        return true;
    }

    public byte[] getFullOutputData() {
        return fullOutputData;
    }

    public String toString() {
        return "SimpleOutputAudioPlayer";
    }

    public void showMetrics() {
    }
}
