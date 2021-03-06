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
public class MulTest {


    private Machine m;
    private Translator t;

    private String fileName = "mulTest.sml";
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
    public void testSimpleMul() throws Exception {

        writer.println("b1 lin 1 4");
        writer.println("b2 lin 2 5");
        writer.println("b3 mul 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),4);
        assertEquals(m.getRegisters().getRegister(2),5);
        assertEquals(m.getRegisters().getRegister(3),20);
    }

    @Test
    public void testSameRegisterMul() throws Exception {

        writer.println("b1 lin 1 5");
        writer.println("b3 mul 1 1 1");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),25);
    }

    @Test (expected=ArrayIndexOutOfBoundsException.class)
    public void testMulRegisterNotExisting() {
        writer.println("b1 lin 33 30");
        writer.println("b2 lin 32 15"); // Register 32 out of bounds
        writer.println("b3 mul 1 32 33");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
    }
}
