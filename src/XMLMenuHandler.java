import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * XML parser for blog article
 */
public class XMLMenuHandler extends DefaultHandler {

    private Map menuMap = new HashMap();
    private JMenuBar menuBar;
    private LinkedList menuList = new LinkedList();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        //Определяем стартовый элемент (menu, menubar, menuitem или separator)
        if(qName.equals("menu")) {
            parseMenu(atts);
        } else if(qName.equals("menubar")) {
            parseMenubar(atts);
        } else if(qName.equals("menuitem")) {
            parseMenuitem(atts);
        } else if(qName.equals("separator")) {
            parseSeparator();
        }
    }

    //Определяем закрывающий тег </menu> для перехода к следующему элементу меню.
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("menu")) {
            menuList.removeFirst();
        }
    }

    private void parseSeparator() {
        //Добавляем разделитель в текущее меню
        ((JMenu)menuList.getFirst()).addSeparator();
    }

    private void parseMenuitem(Attributes atts) {
        //Создаем пункт меню
        JMenuItem menuItem = new JMenuItem();
        String menuItemName = atts.getValue("name");

        //Свойства пункта меню
        parseAttributes(menuItem, atts);
        menuMap.put(menuItemName, menuItem);

        //Добавляем к текущему выпадающему меню
        ((JMenu)menuList.getFirst()).add(menuItem);
    }

    private void parseMenubar(Attributes atts) {
        //Создаем строку меню
        JMenuBar tempMenuBar = new JMenuBar();
        
        String menuBarName = atts.getValue("name");
        menuMap.put(menuBarName, tempMenuBar);
        menuBar = tempMenuBar;
    }

    private void parseMenu(Attributes atts) {
        //Создаем меню
        JMenu menu = new JMenu();
        String menuName = atts.getValue("name");

        //Свойства меню
        parseAttributes(menu, atts);
        menuMap.put(menuName, menu);

        //Создаем меню, если нет ни одного меню-элемента.
        if (menuList.size() == 0) {
            menuBar.add(menu);
        } else { //Добавляем подменю
            ((JMenu)menuList.getFirst()).add(menu);
        }

        //Добавляем меню к остальным меню-элементам
        menuList.addFirst(menu);
    }

    private void parseAttributes(JMenuItem item, Attributes atts) {
        //Получаем аттрибуты
        String text = atts.getValue("text");
        String image = atts.getValue("image");
        String mnemonic = atts.getValue("mnemonic");
        String accelerator = atts.getValue("accelerator");
        String isEnabled = atts.getValue("isEnabled");

        //Настраиваем свойства меню в соответствие с описанными аттрибутами в XML
        item.setText(text);
        if(image != null) {
            item.setIcon(new ImageIcon(getClass().getResource(image.toString())));
        }
        if(mnemonic != null) {
            item.setMnemonic(mnemonic.charAt(0));
        }
        if(accelerator != null) {
            item.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        }
        if(isEnabled != null) {
            item.setEnabled(Boolean.parseBoolean(isEnabled));
        }
    }

    public Map getMenuMap() {
        return menuMap;
    }
}
