package cn.akira.util;

import java.io.IOException;
import java.util.Map;

public class TestUtil {

    public static void main(String[] args) throws IOException {
        Map<String, String> verifyCodeMap = ValidateUtil.generateVerifyCodeImgBase64();
        System.out.println(verifyCodeMap.get("base64"));
        System.out.println(verifyCodeMap.get("verifyCode"));
    }
}
