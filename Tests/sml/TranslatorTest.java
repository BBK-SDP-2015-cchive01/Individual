package sml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by chrischivers on 01/02/15.
 */
public class TranslatorTest {

    private Machine m;
    private Translator t;

    private String fileName = "translatorTest.sml";
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        m = new Machine();
        writer = new PrintWriter("src/" + fileName);
    }

    @After
    public void tearDown() throws Exception {
        File f = new File("src/" + fileName);
        f.delete();
    }

    @Test
    // Tests that method can be read if written in irregular case
    public void testIrregularCaseOnMethod() throws Exception {

        writer.println("b1 LiN 1 30"); // Unknown instruction
        writer.println("b2 LIN 2 15");
        writer.println("b3 aDD 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1), 30);
        assertEquals(m.getRegisters().getRegister(2), 15);
        assertEquals(m.getRegisters().getRegister(3), 45);

    }

    @Test
    // Tests that program continues despite unknown instruction
    public void testUnknownInstruction() throws Exception {

        writer.println("b1 unknown 1 30"); // Unknown instruction
        writer.println("b2 lin 2 30");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(2), 30);

    }

    @Test
    // Tests that program continues despite unknown syntax in an instruction
    public void testIncorrectMethodSyntax () throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b2 lin 2 15");
        writer.println("b3 add 3 1"); // Incorrect syntax
        writer.println("b4 lin 4 20");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1), 30);
        assertEquals(m.getRegisters().getRegister(2), 15);
        assertEquals(m.getRegisters().getRegister(4), 20);

    }
}
