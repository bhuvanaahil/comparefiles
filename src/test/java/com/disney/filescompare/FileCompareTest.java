package com.disney.filescompare;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class FileCompareTest
{

    @Test
    public void compareFilesTest() throws IOException, SAXException {
        FileCompare  fileCompare = new FileCompare();
        fileCompare.compareFiles();
        assertTrue( true );
    }
}
