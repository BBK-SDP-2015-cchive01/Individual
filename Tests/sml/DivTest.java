package sml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by chrischivers on 31/01/15.
 */
public class DivTest {

    private Machine m;
    private Translator t;

    private String fileName = "divTest.sml";
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
    public void testSimpleDiv() throws Exception {

        writer.println("b1 lin 1 4");
        writer.println("b2 lin 2 2");
        writer.println("b3 div 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),4);
        assertEquals(m.getRegisters().getRegister(2),2);
        assertEquals(m.getRegisters().getRegister(3),2);
    }

    @Test
    public void testSameRegisterDiv() throws Exception {

        writer.println("b1 lin 1 10");
        writer.println("b3 div 2 1 1");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(2),1);
    }

    @Test (expected = ArithmeticException.class)
    public void testDivideByZero() {
        writer.println("b1 lin 1 4");
        writer.println("b2 lin 2 0"); // Register 32 out of bounds
        writer.println("b3 div 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
    }
}
