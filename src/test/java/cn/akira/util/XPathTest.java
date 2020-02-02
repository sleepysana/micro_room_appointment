package cn.akira.util;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class XPathTest {

    Document doc = null;
    XPath xpath = null;

    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        // 创建DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setValidating(true);
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
        String path = this.getClass().getResource("/").getPath();
        doc = builder.parse(path+"inventory.xml");

        // 创建XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();
    }

    @Test
    public void test1() throws XPathExpressionException {
        // -----查询作者为Neal Stephenson的图书的标题-----
        // 如果需要执行多次XPath表达式，还是使用这种预编译的方式更好，类似Java的Pattern
        XPathExpression expr = xpath
                .compile("//book[author='Neal Stephenson']/title/text()");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            System.out.println(node.getNodeValue());
        }
        /*输出：
            Snow Crash
            Zodiac
         */
    }

    @Test
    public void test2() throws XPathExpressionException {
        // -----查询1997年之后的图书的标题-----
        // 一次性的表达式直接使用XPath，跳过编译
        NodeList nodes = (NodeList) xpath
                .evaluate("//book[@year>1997]/title/text()", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            System.out.println(node.getNodeValue());
        }
        /* 输出：
            Snow Crash
            Burning Tower
         */
    }

    @Test
    public void test3() throws XPathExpressionException {
        // -----查询1997年之后的图书的标题和属性-----
        NodeList nodes = (NodeList) xpath.evaluate(
                "//book[@year=2000]/title/text() | //book[@year=2000]/@*",
                doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            System.out.println(node.getNodeValue());
        }
        /* 输出：
            2000
            Snow Crash
            2005
            Burning Tower
         */
    }

    @Test
    public void getCompilePath(){
        String path = this.getClass().getResource("/").getPath();
        System.out.println(path);
    }

}
