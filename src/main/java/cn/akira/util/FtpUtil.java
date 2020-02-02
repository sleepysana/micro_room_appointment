package cn.akira.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FtpUtil {
    /**
     * 读取指定ftp上文本文件的内容
     *
     * @param ftpIp    ftp地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @param dirPath  文件所在目录(以"/"开头)
     * @param fileName 文件名
     * @return 文件内容
     * @throws IOException 万一出错了呢？
     */
    public static String readTxtFile(String ftpIp,
                                     int port, String username,
                                     String password,
                                     String dirPath,
                                     String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpIp, port);     //连接ftp
        ftpClient.login(username, password);//登录ftp
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {//是否连接成功,成功true,失败false
            ftpClient.changeWorkingDirectory(dirPath);//找到指定目录
            InputStream inputStream = ftpClient.retrieveFileStream(fileName);//根据目录获取指定文件
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            StringBuilder stringBuilder = new StringBuilder(150);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } else throw new IOException("ftp连接失败");
    }
}
