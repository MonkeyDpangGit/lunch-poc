package com.test.service.marytts;

import java.util.Locale;
import marytts.LocalMaryInterface;
import marytts.util.data.audio.AudioPlayer;

/**
 * MaryTTSService
 *
 * @author torrisli
 * @date 2025/4/22
 * @Description: MaryTTSService
 */
public class MaryTTSService {

    public static void main(String[] args) throws Exception {

        LocalMaryInterface mary = new LocalMaryInterface();
        System.out.println(mary.getAvailableVoices());

        // 设置 Mary 的语言环境
        mary.setVoice("cmu-slt-hsmm");

        String input = "Hello, world!";

        AudioPlayer player = new AudioPlayer(mary.generateAudio(input));
        player.start();
        player.join();
    }
}
