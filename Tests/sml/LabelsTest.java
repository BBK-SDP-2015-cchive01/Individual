package sml;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by chrischivers on 07/02/15.
 */
public class LabelsTest {

    private Labels labs;

    @Before
    public void setUp() throws Exception {
        labs = new Labels();
    }


    @Test
    public void TestAddLabel() throws Exception {
        labs.addLabel("b1");
        assertEquals(labs.indexOf("b1"),0);
    }


    @Test
    public void TestResetLabels() throws Exception {
        labs.addLabel("b1");
        assertEquals(labs.indexOf("b1"),0);
        labs.reset();
        assertEquals(labs.indexOf("b1"),-1);
    }

}
