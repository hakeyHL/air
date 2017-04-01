package com.air.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by hs on 2017年03月31日.
 * Time 17:19
 */
public class ImageUtils {
    public static final char[] codeCollection = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};

    /**
     * 获取随机颜色值
     *
     * @param minColor
     * @param maxColor
     * @return
     */
    private static Color getRandomColor(int minColor, int maxColor) {

        Random random = new Random();
        // 保存minColor最大不会超过255
        if (minColor > 255)
            minColor = 255;
        // 保存minColor最大不会超过255
        if (maxColor > 255)
            maxColor = 255;
        // 获得红色的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        // 获得绿色的随机颜色值
        int green = minColor + random.nextInt(maxColor - minColor);
        // 获得蓝色的随机颜色值
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);

    }

    public static void getValidationCode(HttpServletResponse response, HttpServletRequest request) {

        // 用于保存最后随机生成的验证码
        StringBuilder validationCode = new StringBuilder();

        try {
            // 设置图形验证码的长和宽（图形的大小）
            int width = 70, height = 30;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();// 获得用于输出文字的Graphics对象
            Random random = new Random();
            g.setColor(getRandomColor(180, 250));// 随机设置要填充的颜色
            g.fillRect(0, 0, width, height);// 填充图形背景
            // 设置初始字体
            g.setFont(new Font("Times New Roman", Font.ITALIC, height));
            g.setColor(getRandomColor(120, 180));// 随机设置字体颜色

            //干扰线
            for (int i = 0; i < 155; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(width);
                int y1 = random.nextInt(height);
                g.drawLine(x, y, x + x1, y + y1);
            }

            // 随机生成4个验证码
            // 随机设置当前验证码的字符的字体
            g.setFont(new Font("Courier", Font.PLAIN, 24));
            // 随机获得当前验证码的字符串
            for (int i = 0; i < 4; i++) {
                validationCode.append(codeCollection[new Random().nextInt(codeCollection.length)]);
            }
            // 随机设置当前验证码字符的颜色
            g.setColor(getRandomColor(10, 100));
            // 在图形上输出验证码字符，x和y都是随机生成的
            g.drawString(validationCode.toString(), 16 + random.nextInt(7), height - random.nextInt(6));

            request.getSession().setAttribute("validateCode", validationCode.toString().toLowerCase());
            //名称重置
            ImageIO.write(image, "jpg", response.getOutputStream());
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
