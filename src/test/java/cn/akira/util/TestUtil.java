package cn.akira.util;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Map;

public class TestUtil {

    public static void main(String[] args) throws IOException {
        Map<String, String> verifyCodeMap = ValidateUtil.generateVerifyCodeImgBase64();
        System.out.println(verifyCodeMap.get("base64"));
        System.out.println(verifyCodeMap.get("verifyCode"));
    }

    @Test
    public void rsaDecTest() throws Exception {
        String decrypt = RsaUtil.decrypt("eNYOTi2DL58F5ROfrYJHYHkKFH/Q3iVErNrQGc8+3QW3SQDi1btr7Zsc42KUFgoXUQnUZ8626tAU6f1x6VfXhgmK+ygGec9JUDmND+AWwRb8w9QinEMZ5lDd5ASWm8Z7rUqTIAb64qoTCFYg0e3QcOLY2HDr8i/z2UNPn1jKzJE=");
        System.out.println(decrypt);
    }

    @Test
    public void readFtpFileContentTest() throws IOException {
        String hd_mgr = FtpUtil.readTxtFile
                (
                        "192.168.1.7",
                        21,
                        "hd_mgr",
                        "123",
                        "/10022",
                        "10022_12345"
                );

        System.out.println(hd_mgr);
    }

    @Test
    public void readConfigFileTest() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException {
        String configTagValue = ConfigUtil.getConfigTagValue("headIconFtp", "hostname");
        System.out.println(configTagValue);
    }
}
