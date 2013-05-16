/*
 *   casmi
 *   http://casmi.github.com/
 *   Copyright (C) 2011, Xcoo, Inc.
 *
 *  casmi is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
  
package casmi.io.parser;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import casmi.io.exception.ParserException;
import casmi.io.parser.XML;
import casmi.io.parser.XMLElement;

public class XMLTest {

    private static final String EXAMPLE_XML = XMLTest.class.getResource("example.xml").getPath();
    private static final String WRITE_EXAMPLE_XML = XMLTest.class.getResource("write_example.xml").getPath();
    
    @Test
    public void simpleReadTest() {
        XML xml = new XML();

        try {
            xml.parseFile(new File(EXAMPLE_XML));
        } catch (ParserException e) {
            e.printStackTrace();
            fail("Failed to parse XML.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(xml);
    }

    @Test
    public void readTest() {
        XML xml = new XML();

        try {
            xml.parseFile(new File(EXAMPLE_XML));
        } catch (ParserException e) {
            e.printStackTrace();
            fail("Failed to parse XML.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        recursivePrint(xml, 0);
    }

    private void recursivePrint(XMLElement element, int indent) {
        String indentStr = "";
        for (int i = 0; i < indent; i++) {
            indentStr += "  ";
        }

        // Print start tag and attributes.
        System.out.print(indentStr);
        System.out.print("<");
        System.out.print(element.getName());

        for (String attributeName : element.getAttributeNames()) {
            String value = element.getAttribute(attributeName);
            System.out.print(" " + attributeName + "=\"" + value + "\"");
        }

        System.out.println(">");

        // Print content.
        if (element.hasContent()) {
            System.out.print(indentStr + "  ");
            System.out.println(element.getContent());
        }

        // If this element does not have children, return method.
        if (!element.hasChildren()) {
            // Print end tag.
            System.out.print(indentStr);
            System.out.println("</" + element.getName() + ">");
            return;
        }

        // Execute this method recursively.
        for (XMLElement child : element.getChildren()) {
            recursivePrint(child, indent + 1);
        }

        // Print end tag.
        System.out.print(indentStr);
        System.out.println("</" + element.getName() + ">");
    }

    @Test
    public void writeTest() {
        XML xml = new XML("  ");

        xml.setName("alcohoric");

        XMLElement sake = new XMLElement("sake");
        
        XMLElement urakasumi = new XMLElement("urakasumi", "UraKasumi");
        urakasumi.addAttribute("origin", "Miyagi");
        urakasumi.addAttribute("abv", "15");
        sake.addChild(urakasumi);
        
        xml.addChild(sake);

        try {
            xml.save(new File(WRITE_EXAMPLE_XML));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to generate XML.");
        }
    }

}
