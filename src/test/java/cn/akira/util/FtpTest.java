package cn.akira.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FtpTest {

    private FTPClient ftp;

    /**
     * @param path     上传到ftp服务器哪个路径下
     * @param ftpIp    地址
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    private boolean connect(String path, String ftpIp, int port, String username, String password) {
        boolean result = true;
        ftp = new FTPClient();
        int reply;
        try {
            ftp.connect(ftpIp, port);
            ftp.login(username, password);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                result = false;
            }
            ftp.changeWorkingDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * @param file 上传的文件或文件夹
     * @throws Exception
     */
    private void upload(File file) throws Exception {
        if (file.isDirectory()) {
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (String s : files) {
                File file1 = new File(file.getPath() + "\\" + s);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                } else {
                    File file2 = new File(file.getPath() + "\\" + s);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file2.getName(), input);
                    input.close();
                }
            }
        } else {
            File file2 = new File(file.getPath());
            FileInputStream input = new FileInputStream(file2);
            ftp.storeFile(file2.getName(), input);
            input.close();
        }
    }

    public static void main(String[] args) throws Exception {
        FtpTest t = new FtpTest();
        t.connect("", "localhost", 21, "yhh", "yhhazr");
        File file = new File("e:\\uploadify");
        t.upload(file);
    }

    @Test
    public void readFtpTxtFileTest() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.1.7", 21);//连接ftp
        ftpClient.login("hd_mgr", "123");//登陆ftp
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {//是否连接成功,成功true,失败false
            ftpClient.changeWorkingDirectory("/10022");//找到指定目录
            InputStream inputStream = ftpClient.retrieveFileStream("10022_12345");//根据指定名称获取指定文件
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            StringBuilder stringBuilder = new StringBuilder(150);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String content = stringBuilder.toString();
            System.out.println(content);
        } else throw new IOException("ftp连接失败");
    }
}
