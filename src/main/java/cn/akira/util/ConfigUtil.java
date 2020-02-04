package cn.akira.util;

import cn.akira.pojo.User;
import org.apache.ibatis.jdbc.Null;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {
    static Document doc = null;
    static XPath xpath = null;

    public static String getConfigTagValue(String configId, String propertyName) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        {
            // 创建DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setValidating(true);
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            // 创建DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 设置异常处理对象
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("WARN:" + exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.out.println("error:" + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println("fatalError:" + exception.getMessage());
                }
            });

            // 创建Document
            String path = User.class.getResource("/").getPath();
            doc = builder.parse(path + "extra-config.xml");

            // 创建XPath
            XPathFactory xpathFactory = XPathFactory.newInstance();
            xpath = xpathFactory.newXPath();
        }
        Map<String, NodeList> nodeListMap = getNodes(configId, propertyName);
        NodeList nodes_text = nodeListMap.get("nodes_text");
        NodeList nodes_value = nodeListMap.get("nodes_value");
        NodeList nodes = nodes_text;
        int nodesLength;
        try{
            nodesLength = nodes.getLength();
        }catch (NullPointerException e){
            System.err.println("我也不知道这个异常是怎么回事，有时候会出现有时候又不会出现。应该是什么东西没关闭导致的，\n" +
                    "也有可能是extra-config.xml或者getNodes里面的表达式没写对，反正目前还搞不清楚。");
            return getConfigTagValue(configId, propertyName);
        }

        nodes = nodesLength > 0 ? nodes_text : nodes_value;
        nodesLength = nodes.getLength();//重新再赋值一遍
        if (nodesLength < 1) {
            throw new XPathExpressionException("没有在id为\"" + configId + "\"的config下找到name为\"" + propertyName + "\"的property");
        } else if (nodesLength > 1) {
            throw new XPathExpressionException("id为\"" + configId + "\"的config下有太多name为\"" + propertyName + "\"的property");
        }
        String nodeValue = nodes.item(0).getNodeValue().trim();
        if ("".equals(nodeValue)) {
            nodes = nodes_value;
            nodeValue = nodes.item(0).getNodeValue().trim();
        }
        return nodeValue;
    }

    private static Map<String, NodeList> getNodes(String configId, String propertyName) {
        Map<String, NodeList> nodesMap = new HashMap<>();
        try {
            NodeList nodes_value = (NodeList) xpath
                    .evaluate(
                            "//config[@id='" + configId + "']/property[@name='" + propertyName + "']/@value",
                            doc,
                            XPathConstants.NODESET
                    );
            NodeList nodes_text = (NodeList) xpath
                    .evaluate(
                            "//config[@id='" + configId + "']/property[@name='" + propertyName + "']/text()",
                            doc,
                            XPathConstants.NODESET
                    );
            nodesMap.put("nodes_value", nodes_value);
            nodesMap.put("nodes_text", nodes_text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return nodesMap;
    }

    public static Map<String, String> getRsaKeys() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        Map<String, String> rsaKeysMap = new HashMap<>();
        String configId = "rsa";
        String publicKey = "publicKey";
        String privateKey = "privateKey";
        rsaKeysMap.put(publicKey, getConfigTagValue(configId, publicKey));
        rsaKeysMap.put(privateKey, getConfigTagValue(configId, privateKey));
        return rsaKeysMap;
    }

    public static String getRsaPublicKey() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        return getRsaKeys().get("publicKey");
    }

    public static String getRsaPrivateKey() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        return getRsaKeys().get("privateKey");
    }

    public static Map<String, Object> getHeadIconFtpInfo() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        Map<String, Object> rsaKeysMap = new HashMap<>();
        String configId = "headIconFtp";
        String hostname = "hostname";
        String port = "port";
        String username = "username";
        String password = "password";
        rsaKeysMap.put(hostname, getConfigTagValue(configId, hostname));
        rsaKeysMap.put(port, Integer.valueOf(getConfigTagValue(configId, port)));
        rsaKeysMap.put(username, getConfigTagValue(configId, username));
        rsaKeysMap.put(password, getConfigTagValue(configId, password));

        return rsaKeysMap;
    }

    public static String getHeadIconFtpIp() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        return (String) getHeadIconFtpInfo().get("hostname");
    }

    public static int getHeadIconFtpPort() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, TransformerException {
        return (int) getHeadIconFtpInfo().get("port");
    }

    public static String getHeadIconFtpUsername() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, TransformerException {
        return (String) getHeadIconFtpInfo().get("username");
    }

    public static String getHeadIconFtpPassword() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, TransformerException {
        return (String) getHeadIconFtpInfo().get("password");
    }
}
