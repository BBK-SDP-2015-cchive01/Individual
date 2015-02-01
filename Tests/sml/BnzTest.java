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
public class BnzTest {


    private Machine m;
    private Translator t;

    private String fileName = "bnzTest.sml";
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
    public void testBnzNotExecuted() throws Exception {

        writer.println("b1 lin 1 0");
        writer.println("b2 bnz 1 b1");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
    }

    @Test
    public void testBnzExecuted() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b2 sub 1 1 1");
        writer.println("b3 bnz 1 b2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),0);
    }

    @Test (expected=ArrayIndexOutOfBoundsException.class)
    public void testBnzToUnknownLabel() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b2 bnz 1 b6");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),0);
    }


}
