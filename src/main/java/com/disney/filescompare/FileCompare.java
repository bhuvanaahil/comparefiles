package com.disney.filescompare;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileCompare {

    public void compareFiles() throws IOException, SAXException {
        InputStream fis1 = null;
        InputStream fis2 = null;
        BufferedReader src = null;
        BufferedReader target = null;

        try {
            fis1 = getClass()
                    .getClassLoader().getResourceAsStream("XMLCompareFile1.xml");
            fis2 = getClass()
                    .getClassLoader().getResourceAsStream("XMLCompareFile2.xml");
            src = new BufferedReader(new InputStreamReader(fis1));
            target = new BufferedReader(new InputStreamReader(fis2));
            diff(src, target);
        } catch (IOException e) {
            throw e;
        } catch (SAXException e) {
            throw e;
        } finally {
            try {
                if (target != null) target.close();
                if (src != null) src.close();
                if (fis1 != null) fis1.close();
                if (fis2 != null) fis2.close();
            } catch (IOException e) {

            }
        }

    }

    private void diff(Reader src, Reader target) throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        //creating Diff instance to compare two XML files
        Diff xmlDiff = new Diff(src, target);

        //for getting detailed differences between two xml files
        DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);
        List<Difference> differences = detailXmlDiff.getAllDifferences();

        int totalDifferences = differences.size();
        System.out.println("===============================");
        System.out.println("Total Number of differences : "+totalDifferences);
        System.out.println("================================");

        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("XML File Difference");
        int rowCount = 0;
        for (Difference difference : differences) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            Cell cell = row.createCell(++columnCount);
            cell.setCellValue(difference.toString());
        }


        FileOutputStream out = null;
        try {
            String path = "src/main/resources";

            File file = new File(path);
            String absolutePath = file.getAbsolutePath();
            out = new FileOutputStream(new File(absolutePath + "/compare_result.xlsx"));
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if(out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

