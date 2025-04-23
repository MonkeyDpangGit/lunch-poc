package com.test.service.tesseract;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * TesseractService
 *
 * @author torrisli
 * @date 2025/4/22
 * @Description: TesseractService
 */
public class TesseractService {

    public static void main(String[] args) {

        // 指定 Tesseract 的路径
        String tesseractPath = "/usr/local/bin/tesseract";
        System.setProperty("jna.library.path", tesseractPath);

        // 创建 Tesseract OCR 实例
        ITesseract tesseract = new Tesseract();

        // 设置 Tesseract 数据路径，确保这里指向 tessdata 文件夹所在路径
        tesseract.setDatapath("/usr/local/Cellar/tesseract/5.5.0_1/share/tessdata");

        // 如果需要识别非英语文本，可以设置语言（例如中文："chi_sim"）
        tesseract.setLanguage("eng");

        try {
            // 输入图片路径
            File imageFile = new File("/Users/lijunhao/IdeaProjects/lunch-poc/test/src/main/resources/tessdata/test.png");

            // 执行 OCR 识别
            String result = tesseract.doOCR(imageFile);

            // 打印 OCR 结果
            System.out.println("OCR Result:");
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println("Error while performing OCR: " + e.getMessage());
        }
    }
}
