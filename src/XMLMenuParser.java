import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class XMLMenuParser {
    //Наш XML
    private InputSource inputSource;
    //Парсер XML
    private SAXParser saxParser;
    //Наша реализация обработчика
    private XMLMenuHandler xmlMenuHandler;

    public XMLMenuParser(InputStream inputStream) {
        //Загружаем XML файл и инициализируем XML обработчки и парсер.
        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            inputSource = new InputSource(reader);
            saxParser = SAXParserFactory.newInstance().newSAXParser();
            xmlMenuHandler = new XMLMenuHandler();
        } catch (Exception e) {
            System.out.println("Something went wrong during SAXParser initialization: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void parseXML() throws IOException, SAXException {
        saxParser.parse(inputSource, xmlMenuHandler);
    }

    public JMenu getMenu(String name) {
        return (JMenu)xmlMenuHandler.getMenuMap().get(name);
    }

    public JMenuBar getMenuBar(String name) {
        return (JMenuBar)xmlMenuHandler.getMenuMap().get(name);
    }

    public JMenuItem getMenuItem(String name) {
        return (JMenuItem)xmlMenuHandler.getMenuMap().get(name);
    }
}
