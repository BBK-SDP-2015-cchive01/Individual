package sml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class AddTest {

    private Machine m;
    private Translator t;

    private String fileName = "addTest.sml";
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
    public void testSimpleAdd() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b2 lin 2 15");
        writer.println("b3 add 3 1 2");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(1),30);
        assertEquals(m.getRegisters().getRegister(2),15);
        assertEquals(m.getRegisters().getRegister(3),45);
    }

    @Test
    public void testSameRegisterAdd() throws Exception {

        writer.println("b1 lin 1 30");
        writer.println("b3 add 2 1 1");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
        assertEquals(m.getRegisters().getRegister(2),60);
    }

    @Test (expected=ArrayIndexOutOfBoundsException.class)
    public void testAddRegisterNotExisting() {
        writer.println("b1 lin 33 30");
        writer.println("b2 lin 32 15"); // Register 32 out of bounds
        writer.println("b3 add 1 32 33");
        writer.close();


        t = new Translator(fileName);
        t.readAndTranslate(m.getLabels(), m.getProg());
        m.execute();
    }


}