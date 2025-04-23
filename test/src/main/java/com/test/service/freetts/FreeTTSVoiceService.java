package com.test.service.freetts;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * VoiceService
 *
 * @author torrisli
 * @date 2025/4/21
 * @Description: VoiceService
 */
public class FreeTTSVoiceService {

    static {
        // 设置语音资源路径
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }

    /**
     * 将文本转换为音频播放器
     *
     * @param text
     * @return
     */
    private static SimpleOutputAudioPlayer textToAudioPlayer(String text) {

        // 初始化语音管理器
        VoiceManager voiceManager = VoiceManager.getInstance();

        Voice voice = null;
        try {
            // 获取指定的语音
            voice = voiceManager.getVoice("kevin16"); // 或 "kevin"、"kevin16" 等
            if (voice == null) {
                throw new RuntimeException("无法找到指定的语音：kevin16");
            }

            // 分配语音资源
            voice.allocate();

            // 设置语音属性
            voice.setRate(150); // 设置语速（单位：单词/分钟）
            voice.setPitch(100); // 设置音调（单位：赫兹）
            voice.setVolume(3.0f); // 设置音量（范围：0.0 到 5.0）

            // 创建音频播放器并设置输出
            SimpleOutputAudioPlayer audioPlayer = new SimpleOutputAudioPlayer(Type.WAVE);
            // 将音频播放器绑定到语音对象
            voice.setAudioPlayer(audioPlayer);

            // 播放语音
            voice.speak(text);

            return audioPlayer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (voice != null) {
                // 释放语音资源
                voice.deallocate();
            }
        }
    }

    /**
     * 将文本转换为音频数据
     *
     * @param text
     * @param audioFormat
     * @return
     */
    public static byte[] textToAudioData(String text, AudioFormat audioFormat) {

        SimpleOutputAudioPlayer audioPlayer = textToAudioPlayer(text);
        // 关闭音频播放器并完成输出
        audioPlayer.close();

        byte[] byteArray = audioPlayer.getFullOutputData();

        return byteArray;
    }

    /**
     * 将文本转换为音频文件
     *
     * @param text
     * @param audioFormat
     * @param audioFilePath
     */
    public static void textToAudioFile(String text, AudioFormat audioFormat, String audioFilePath) {

        SimpleOutputAudioPlayer audioPlayer = textToAudioPlayer(text);
        // 关闭音频播放器并完成输出
        audioPlayer.close();

        byte[] byteArray = audioPlayer.getFullOutputData();

        InputStream inputStream = null;
        AudioInputStream ais = null;
        try {
            inputStream = new ByteArrayInputStream(byteArray);
            ais = new AudioInputStream(inputStream,
                    Optional.ofNullable(audioFormat).orElse(audioPlayer.getAudioFormat()), byteArray.length);
            AudioSystem.write(ais, Type.WAVE, new File(audioFilePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将文本转换为音频流
     *
     * @param text
     * @param audioFormat
     * @param outputStream
     */
    public static void textToAudioOutputStream(String text, AudioFormat audioFormat, OutputStream outputStream) {

        SimpleOutputAudioPlayer audioPlayer = textToAudioPlayer(text);
        // 关闭音频播放器并完成输出
        audioPlayer.close();

        byte[] byteArray = audioPlayer.getFullOutputData();

        InputStream inputStream = null;
        AudioInputStream ais = null;
        try {
            inputStream = new ByteArrayInputStream(byteArray);
            ais = new AudioInputStream(inputStream,
                    Optional.ofNullable(audioFormat).orElse(audioPlayer.getAudioFormat()), byteArray.length);
            AudioSystem.write(ais, Type.WAVE, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        // normal audio format
        AudioFormat audioFormat = new AudioFormat(22000.0f, 16, 1, true, true);
        String audioFilePath = "/Users/lijunhao/Desktop/log/20250421/output3.wav";
        String text = "Hello, welcome to the Java FreeTTS demo! This is a simple text-to-speech example.";

        textToAudioFile(text, null, audioFilePath);
    }
}
