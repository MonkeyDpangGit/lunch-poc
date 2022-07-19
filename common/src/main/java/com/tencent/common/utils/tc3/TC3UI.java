package com.tencent.common.utils.tc3;

import com.tencent.common.constants.TC3Constants;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;

/**
 * 云API v3客户端工具
 *
 * @author torrisli
 * @date 2020/12/19
 * @Description: 云API v3客户端工具
 */
public class TC3UI extends JFrame {

    /**
     * methodText
     */
    private JTextField methodText;

    /**
     * timestampText
     */
    private JTextField timestampText;

    /**
     * dateText
     */
    private JTextField dateText;

    /**
     * ctText
     */
    private JTextField ctText;

    /**
     * serviceText
     */
    private JTextField serviceText;

    /**
     * hostText
     */
    private JTextField hostText;

    /**
     * secretIdText
     */
    private JTextField secretIdText;

    /**
     * secretKeyText
     */
    private JTextField secretKeyText;

    /**
     * actionText
     */
    private JTextField actionText;

    /**
     * versionText
     */
    private JTextField versionText;

    /**
     * regionText
     */
    private JTextField regionText;

    /**
     * shText
     */
    private JTextField shText;

    /**
     * queryStringText
     */
    private JTextField queryStringText;

    /**
     * payloadArea
     */
    private JTextArea payloadArea;

    /**
     * headerArea
     */
    private JTextArea headerArea;

    /**
     * resultArea
     */
    private JTextArea resultArea;

    /**
     * genTimestampBtn
     */
    private JButton genTimestampBtn;

    /**
     * genHeaderBtn
     */
    private JButton genHeaderBtn;

    /**
     * resetBtn
     */
    private JButton resetBtn;

    /**
     * TC3UI
     */
    public TC3UI() {

        setSize(680, 780);
        setTitle("云API v3工具");

        setLayout(new FlowLayout());

        JLabel methodLabel = new JLabel(StringUtils.leftPad("method", 15));
        add(methodLabel);
        methodText = new JTextField(18);
        add(methodText);

        JLabel serviceLabel = new JLabel(StringUtils.leftPad("service", 15));
        add(serviceLabel);
        serviceText = new JTextField(18);
        add(serviceText);

        JLabel hostJLabel = new JLabel(StringUtils.leftPad("Host", 15));
        add(hostJLabel);
        hostText = new JTextField(18);
        add(hostText);

        JLabel ctJLabel = new JLabel(StringUtils.leftPad("Content-type", 15));
        add(ctJLabel);
        ctText = new JTextField(18);
        add(ctText);

        JLabel timestampLabel = new JLabel(StringUtils.leftPad("timestamp", 15));
        add(timestampLabel);
        timestampText = new JTextField(18);
        add(timestampText);

        JLabel dateLabel = new JLabel(StringUtils.leftPad("date", 15));
        add(dateLabel);
        dateText = new JTextField(18);
        add(dateText);

        JLabel shJLabel = new JLabel(StringUtils.leftPad("SignedHeaders", 15));
        add(shJLabel);
        shText = new JTextField(18);
        add(shText);

        JLabel actionJLabel = new JLabel(StringUtils.leftPad("Action", 15));
        add(actionJLabel);
        actionText = new JTextField(18);
        add(actionText);

        JLabel versionJLabel = new JLabel(StringUtils.leftPad("Version", 15));
        add(versionJLabel);
        versionText = new JTextField(18);
        add(versionText);

        JLabel regionJLabel = new JLabel(StringUtils.leftPad("Region", 15));
        add(regionJLabel);
        regionText = new JTextField(18);
        add(regionText);

        JLabel queryStringJLabel = new JLabel(StringUtils.leftPad("QueryString", 15));
        add(queryStringJLabel);
        queryStringText = new JTextField(45);
        add(queryStringText);

        JLabel secretIdJLabel = new JLabel(StringUtils.leftPad("secretId", 15));
        add(secretIdJLabel);
        secretIdText = new JTextField(45);
        add(secretIdText);

        JLabel secretKeyJLabel = new JLabel(StringUtils.leftPad("secretKey", 15));
        add(secretKeyJLabel);
        secretKeyText = new JTextField(45);
        add(secretKeyText);

        JLabel payloadJLabel = new JLabel(StringUtils.leftPad("payload", 15));
        add(payloadJLabel);
        payloadArea = new JTextArea(12, 45);
        payloadArea.setLineWrap(true);
        add(payloadArea);

        JLabel headerJLabel = new JLabel(StringUtils.leftPad("header", 15));
        add(headerJLabel);
        headerArea = new JTextArea(12, 45);
        headerArea.setLineWrap(true);
        add(headerArea);

        JLabel resultJLabel = new JLabel(StringUtils.leftPad("result", 15));
        add(resultJLabel);
        resultArea = new JTextArea(3, 45);
        resultArea.setLineWrap(true);
        add(resultArea);

        genTimestampBtn = new JButton("生成timestamp");
        genTimestampBtn.addActionListener(new JFrameListener());
        add(genTimestampBtn);

        genHeaderBtn = new JButton("生成header");
        genHeaderBtn.addActionListener(new JFrameListener());
        add(genHeaderBtn);

        resetBtn = new JButton("重置数据");
        resetBtn.addActionListener(new JFrameListener());
        add(resetBtn);

        initData();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String[] args) {

        new TC3UI();
    }

    /**
     * 初始化数据
     */
    public void initData() {

        // init data
        methodText.setText("POST");
        serviceText.setText("cvm");
        hostText.setText("cvm.tencentcloudapi.com");
        ctText.setText("application/json; charset=utf-8");
        String timestamp = "1551113065";
        timestampText.setText(timestamp);
        String date = TC3Utils.timestampToDateStr(timestamp);
        dateText.setText(date);
        shText.setText("content-type;host");
        actionText.setText("DescribeInstances");
        versionText.setText("2017-03-12");
        regionText.setText("ap-guangzhou");
        queryStringText.setText("");
        secretIdText.setText("AKIDz8krbsJ5yKBZQpn74WFkmLPx3*******");
        secretKeyText.setText("Gu5t9xGARNpq86cd98joQYCN3*******");
        payloadArea.setText(
                "{\"Limit\": 1, \"Filters\": [{\"Values\": [\"\\u672a\\u547d\\u540d\"], \"Name\": \"instance-name\"}]}"
        );
    }

    /**
     * The type J frame listener.
     */
    public class JFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == genTimestampBtn) {
                String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                String date = TC3Utils.timestampToDateStr(timestamp);
                timestampText.setText(timestamp);
                dateText.setText(date);
                return;
            }

            if (e.getSource() == genHeaderBtn) {

                String host = hostText.getText();
                String payload = payloadArea.getText();
                String action = actionText.getText();
                String version = versionText.getText();
                String secretId = secretIdText.getText();

                TC3Request tc3Request = TC3Request.build(host, payload, action, version, secretId);
                // build方法具有的默认值
                tc3Request.setSignedHeaders(shText.getText());
                tc3Request.setTimestamp(timestampText.getText());
                tc3Request.setDate(dateText.getText());
                tc3Request.setService(serviceText.getText());

                Map<String, String> headers = new HashMap<>();
                headers.put(TC3Constants.CONTENT_TYPE.toLowerCase(), ctText.getText());
                headers.put(TC3Constants.HOST.toLowerCase(), host);
                tc3Request.setHeaders(headers);

                // 可选值
                tc3Request.setRegion(regionText.getText());

                // 输入值
                tc3Request.setHttpRequestMethod(methodText.getText());
                tc3Request.setCanonicalQueryString(queryStringText.getText());
                tc3Request.setPayload(payload);

                // 默认输入值计算的签名结果：2230eefd229f582d8b1b891af7107b91597240707d778ab3738f756258d7652c
                StringBuilder rsHeaderSb = new StringBuilder();
                Map<String, String> rsHeader = null;
                try {
                    rsHeader = TC3Utils.generateHeader(tc3Request, secretKeyText.getText());
                    rsHeader.forEach((k, v) -> {
                        rsHeaderSb.append(k);
                        rsHeaderSb.append(": ");
                        rsHeaderSb.append(v);
                        rsHeaderSb.append(TC3Constants.LINEFEED);
                    });

                    headerArea.setText(rsHeaderSb.toString());
                    resultArea.setText("生成请求头成功");

                } catch (Exception e1) {
                    resultArea.setText("生成请求头失败，" + e1.getMessage());
                    return;
                }

                return;
            }

            if (e.getSource() == resetBtn) {
                initData();
            }
        }

    }
}
