package com.mt.bot.easyBot.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class Base64File {
    /**
     * 输入转化后的base64和要生成的问文件位置
     * @param base64
     * @param filePath
     * @return
     */
    public static  String decryptByBase64(String base64, String filePath) {
        if (base64 == null && filePath == null) {
            return "生成文件失败，请给出相应的数据。";
        }
        try {
            Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);
        } catch (IOException e) {
             throw new RuntimeException(e);
        }
        return "指定路径下生成文件成功！";
    }
}
