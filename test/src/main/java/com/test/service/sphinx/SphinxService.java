package com.test.service.sphinx;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * SphinxService
 *
 * @author torrisli
 * @date 2025/4/22
 * @Description: SphinxService
 */
public class SphinxService {

    public static void main(String[] args) throws Exception {

        // 配置识别参数
        Configuration configuration = new Configuration();
//        // 设置声学模型路径
//        configuration.setAcousticModelPath("resource:/sphinx/cmusphinx-zh-cn-5.2/zh_cn.cd_cont_5000");
//        // 设置语言模型路径
//        configuration.setLanguageModelPath("resource:/sphinx/cmusphinx-zh-cn-5.2/zh_cn.lm.bin");
//        // 设置词典路径
//        configuration.setDictionaryPath("resource:/sphinx/cmusphinx-zh-cn-5.2/zh_cn.dic");

        // 设置声学模型路径
        configuration.setAcousticModelPath("resource:/sphinx/cmusphinx-en-in-8khz-5.2/en_in.cd_cont_5000");
        // 设置语言模型路径
        configuration.setLanguageModelPath("resource:/sphinx/cmusphinx-en-in-8khz-5.2/en-us.lm.bin");
        // 设置词典路径
        configuration.setDictionaryPath("resource:/sphinx/cmusphinx-en-in-8khz-5.2/en_in.dic");

//        // 创建语音识别器
//        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
//        // 开始识别
//        recognizer.startRecognition(true);

        // 指定要识别的音频文件路径
        File audioFile = new File("/Users/lijunhao/Desktop/log/20250421/output3.wav");
        InputStream stream = new FileInputStream(audioFile);
        // 创建文音识别器
        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        recognizer.startRecognition(stream);

        SpeechResult result;
        // 循环获取识别结果
        while ((result = recognizer.getResult()) != null) {
            System.out.println("识别结果: " + result.getHypothesis());
        }

        // 停止识别
        recognizer.stopRecognition();
    }
}
