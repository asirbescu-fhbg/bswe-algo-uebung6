package org.lecture;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TestHelper {
    static final PrintStream backupSysOut = System.out;
    static final InputStream backupSysIn = System.in;

    static final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


}
