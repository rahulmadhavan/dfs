package edu.rahulk.cs8982.singlefs.task;



import edu.rahulk.cs8982.singlefs.api.impl.ProviderAPIImpl;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnection;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnector;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderIndexerImpl;
import edu.rahulk.cs8982.singlefs.models.ProviderSerializerImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by rahulk on 2/12/16.
 */
public class MedicalDataReader {

    public static void main(String[] args) {

        SXSSFWorkbook wb = null;
        XSSFWorkbook swb = null;
        try {

            File file = new File("/Users/rahulk/Documents/developer/learn/neu/distsys/assignments/assignment1/Medicare_Provider_Util_Payment_PUF_kl_CY2013.xlsx");


            try {

                Indexer<Provider> indexer = new ProviderIndexerImpl();
                Serializer<Provider> serializer = new ProviderSerializerImpl();
                SimpleDBConnector simpleDBConnector = new SimpleDBConnector<>(indexer, serializer);
                SimpleDBConnection<Provider> simpleDBConnection = null;
                try {
                    simpleDBConnection = simpleDBConnector.createConnection("provider");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println(System.currentTimeMillis());
                MyXLSXReader mySheetContentsHandler = new MyXLSXReader();

                OPCPackage pkg = OPCPackage.open(file);

                XSSFReader reader = new XSSFReader(pkg);

                StylesTable styles = reader.getStylesTable();

                ReadOnlySharedStringsTable sharedStrings = new ReadOnlySharedStringsTable(pkg);

                ContentHandler handler = new XSSFSheetXMLHandler(styles, sharedStrings, mySheetContentsHandler, true);

                XMLReader parser = XMLReaderFactory.createXMLReader();
                parser.setContentHandler(handler);
                parser.parse(new InputSource(reader.getSheetsData().next()));

                ProviderAPIImpl providerAPI = new ProviderAPIImpl();
                providerAPI.setDBConnection(simpleDBConnection);
                System.out.println(System.currentTimeMillis());
                providerAPI.putProviders(mySheetContentsHandler.providers);
                System.out.println(mySheetContentsHandler.count);
                System.out.println(System.currentTimeMillis());

            } catch (OpenXML4JException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}
