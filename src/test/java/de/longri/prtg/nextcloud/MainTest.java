package de.longri.prtg.nextcloud;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    static final PrintStream ORIGINAL_OUT = System.out;

    public static ByteArrayOutputStream pipeOutput() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        System.setErr(out);
        return baos;
    }

    public static void releaseOutPipe() {
        System.setOut(ORIGINAL_OUT);
        System.setErr(ORIGINAL_OUT);
    }

    @Test
    public void test() {
        synchronized (ORIGINAL_OUT) {
            ByteArrayOutputStream outputStream = pipeOutput();
            String expected = "<prtg>\n" +
                    "\t<error>1</error>\n" +
                    "\t<text>No sensor specified</text>\n" +
                    "</prtg>\n";

            Main.run(new String[]{});
            releaseOutPipe();

            assertEquals(expected, outputStream.toString());
        }

    }

    @Test
    public void test2() {
        synchronized (ORIGINAL_OUT) {
            ByteArrayOutputStream outputStream = pipeOutput();
            String expected = "<prtg>\n" +
                    "\t<error>1</error>\n" +
                    "\t<text>No sensor specified</text>\n" +
                    "</prtg>\n";

            Main.run(new String[]{"\n" +
                    "\n" +
                    "    1 [>---------------------------]\"ahoepfner\",\"André Höpfner\",\"2024-06-26T09:38:33+00:00\",\"none\",0,4,0,0,0\n" +
                    "\"mkober\",\"Marco Kober\",\"2024-06-26T09:38:33+00:00\",\"none\",8681,20,1,0,0\n" +
                    "\"nextcloud\",\"nextcloud\",\"2024-06-26T09:38:33+00:00\",-2,39171419,50,0,0,0\n" +
                    "\"oberger\",\"Oliver Berger\",\"2024-06-26T09:38:33+00:00\",10737418240,0,4,0,0,0\n" +
                    "\"shesse\",\"Sven Hesse\",\"2024-06-26T09:38:33+00:00\",10737418240,38663514,38,5,0,0"
                    , "-d=Name[:]"
                    , "-d=Name2[:var:www:]"
            });
            releaseOutPipe();

//            assertEquals(expected, outputStream.toString());
        }

    }

    @Test
    public void test3() {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<prtg>\n" +
                "\t<result>\n" +
                "\t\t<channel>Api-test-Usr</channel>\n" +
                "\t\t<value>0.0</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>0,00 Bytes/1,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>Api-test-Usr2</channel>\n" +
                "\t\t<value>0.0</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>0,00 Bytes/1,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>Andre Höpfner</channel>\n" +
                "\t\t<value>0.0</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>0,00 Bytes/1,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>Kathrin Höpfner</channel>\n" +
                "\t\t<value>0.92</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>189,16 MB/20,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>Longri</channel>\n" +
                "\t\t<value>44.9</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>13,47 GB/30,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>nextcloud-admin</channel>\n" +
                "\t\t<value>2.4</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>24,57 MB/1,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<result>\n" +
                "\t\t<channel>prtgtestuser</channel>\n" +
                "\t\t<value>0.0</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<Text>0,00 Bytes/1,00 GB</Text>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>90</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>75</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>Warning</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>Error</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<text>Message for Sensor</text>\n" +
                "</prtg>\n";

        String testArgs = "    1 [>---------------------------]\n" +
                "    3 [->--------------------------]\"Api-test-Usr\",\"Api-test-Usr\",\"2024-07-10T07:55:26+00:00\",1073741824,0,3,0,0,0\n" +
                "\"Api-test-Usr2\",\"Api-test-Usr2\",\"2024-07-10T07:55:26+00:00\",1073741824,0,3,0,0,0\n" +
                "\"Develop\",\"Andre Höpfner\",\"2024-07-10T07:55:26+00:00\",1073741824,0,2,0,0,0\n" +
                "\"Kajinxx\",\"Kathrin Höpfner\",\"2024-07-10T07:55:26+00:00\",21474836480,198347073,234,2,0,0\n" +
                "\"Longri\",\"Longri\",\"2024-07-10T07:55:26+00:00\",32212254720,14461741928,8893,4,0,0\n" +
                "\"nextcloud-admin\",\"nextcloud-admin\",\"2024-07-10T07:55:26+00:00\",1073741824,25765099,49,0,0,0\n" +
                "\"prtgtestuser\",\"prtgtestuser\",\"2024-07-10T07:55:26+00:00\",1073741824,0,3,0,0,0\n";
        ;


        ByteArrayOutputStream outputStream = pipeOutput();
        Main.run(new String[]{testArgs, "-d=Name2[:var:www:]\n"});

        releaseOutPipe();

            assertEquals(expected, outputStream.toString());
    }


}
