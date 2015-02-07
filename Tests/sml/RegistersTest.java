package sml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by chrischivers on 07/02/15.
 */
public class RegistersTest {

    private Registers reg;

    @Before
    public void setUp() throws Exception {
       reg = new Registers();
    }


    @Test
    public void TestSetRegisters() throws Exception {
        reg.setRegister(6,900);
        assertEquals(reg.getRegister(6),900);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsRegisterSet() throws Exception {
        reg.setRegister(33,900);

    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsRegisterGet() throws Exception {
        reg.getRegister(33);

    }
}
