package edu.rahulk.cs8982.singlefs.task;

import edu.rahulk.cs8982.singlefs.models.Provider;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rahulk on 2/12/16.
 */
public class MyXLSXReader implements XSSFSheetXMLHandler.SheetContentsHandler {

    public int count=0;

    Provider provider;
    List<Provider> providers;


    public MyXLSXReader() {
        providers = new LinkedList<>();
    }


    void reset() {
        provider = null;
    }


    @Override
    public void startRow(int rowNum) {
        count++;
        reset();
        provider = new Provider();
        provider.setRow(rowNum + "");
    }

    @Override
    public void endRow() {
        providers.add(provider);
    }

    @Override
    public void cell(String cellReference, String formattedValue) {
        Character c = cellReference.charAt(0);
        Character c2 = cellReference.charAt(1);
        if(c!=null && !(c2 == 'A' || c2 == 'B')) {
            switch (cellReference.charAt(0)) {
                case 'A' : provider.setProviderId(formattedValue); break;
                case 'C' : provider.setName(formattedValue); break;
                case 'H' : provider.setStreet1(formattedValue); break;
                case 'I' : provider.setStreet2(formattedValue); break;
                case 'J' : provider.setCity(formattedValue); break;
                case 'K' : provider.setZipCode(formattedValue); break;
                case 'Q' : provider.setProcedureCode(formattedValue); break;
                case 'T' : provider.setNosOfProcedures(formattedValue); break;
                case 'F' : provider.setGender(formattedValue); break;
                case 'L' : provider.setState(formattedValue); break;
            }
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        System.out.println("text " +text + " isHeader " + isHeader + " tagName "+tagName);
    }
}
