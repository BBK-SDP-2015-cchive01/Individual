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
public class SubTest {

    private Machine m;
    private Translator t;

    private String fileName = "subTest.sml";
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
    public void testSimpleSub() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b2 lin 2 15");
        writer.println("b3 sub 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),30);
        assertEquals(m.getRegisters().getRegister(2),15);
        assertEquals(m.getRegisters().getRegister(3),15);
    }

    @Test
    public void testSameRegisterSub() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b3 sub 1 1 1");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),0);
    }

}
