package rolit.test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.networking.common.PacketArgs;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martijn on 31-1-14.
 */
public class PacketArgsTest {

    PacketArgs packetArgs;
    Object[] data;
    PacketArgs.ArgumentType argumentType1;
    PacketArgs.ArgumentType argumentType2;

    @Before
    public void setUp(){
        data = new Object[4];
        Boolean bool = true;
        Integer integer = new Integer(3);
        String string = "String";
        String[] stringArray = new String[2];
        stringArray[0] = "String";
        stringArray[1] = "Array";
        data[0] = bool;
        data[1] = integer;
        data[2] = stringArray;
        data[3] = string;
        packetArgs = new PacketArgs(data);
    }

    /**
     * Test of de goede objecten uit een pakketje worden gehaald.
     * @throws Exception
     */
    @Test
    public void testGetInt() throws Exception {
        assertEquals(3, packetArgs.getInt(1));
    }

    /**
     * Test of de goede objecten uit een pakketje worden gehaald.
     * @throws Exception
     */
    @Test
    public void testGetBool() throws Exception {
        assertEquals(true, packetArgs.getBool(0));
    }

    /**
     * Test of de goede objecten uit een pakketje worden gehaald.
     * @throws Exception
     */
    @Test
    public void testGetString() throws Exception {
        assertEquals("String", packetArgs.getString(3));
    }

    /**
     * Test of de goede objecten uit een pakketje worden gehaald.
     * @throws Exception
     */
    @Test
    public void testGetMultiString() throws Exception {
        String[] stringRay = new String[2];
        stringRay[0] = "String";
        stringRay[1] = "Array";
        assertEquals(stringRay[0], packetArgs.getMultiString(2)[0]);
        assertEquals(stringRay[1], packetArgs.getMultiString(2)[1]);
    }

    /**
     * Test of van een stringarray de losse strings gehaald worden.
     * @throws Exception
     */
    @Test
    public void testGetSpacedString() throws Exception {
        assertEquals("String Array", packetArgs.getSpacedString(2));
    }

    /**
     * Test of van een string met losse woorden een stringarray kan gemaakt worden.
     * @throws Exception
     */
    @Test
    public void testSpacedToMulti() throws Exception {
        String check = packetArgs.getSpacedString(2);
        String[] object = new String[2];
        object[0] = packetArgs.getMultiString(2)[0];
        object[1] = packetArgs.getMultiString(2)[1];
        assertEquals(object[0], packetArgs.spacedToMulti(check)[0]);
        assertEquals(object[1], packetArgs.spacedToMulti(check)[1]);
    }
}
