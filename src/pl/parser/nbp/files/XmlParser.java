package pl.parser.nbp.files;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pl.parser.nbp.data.AskBidEntry;

/**
 * Parses xml files containing currency data
 * @author Michał Lal
 */
public class XmlParser {

    private final List<Double> parsedAsksList;
    private final List<Double> parsedBidsList;
    private final String currency;
    private static final String ASK_TAG = "kurs_kupna";
    private static final String BID_TAG = "kurs_sprzedazy";
    private static final String CURRENCY_TAG = "kod_waluty";
    public static final String XML_FILE_PATH = "http://www.nbp.pl/kursy/xml/";
    public static final String XML_FILE_EXTENSION = ".xml";
    public static final String XML_FILE_ENCODING = "ISO-8859-2";

    /**
     * XML Parser constructor
     * @param currency currency searched in xml files
     */
    public XmlParser(String currency) {
        parsedAsksList = new ArrayList<>();
        parsedBidsList = new ArrayList<>();
        this.currency = currency;
    }

    public List<Double> getParsedAsksList() {
        return parsedAsksList;
    }

    public List<Double> getParsedBidsList() {
        return parsedBidsList;
    }

    /**
     * Downloads and processes files from given list, collecting ask and bid values to lists
     * @param xmlFileNames list of xml file names to parse
     * @throws java.lang.Exception when there is a problem with connection to NBP server
     */
    public void parseFiles(List<String> xmlFileNames) throws Exception {
        for (String xmlFileName : xmlFileNames) {
            parseFile(xmlFileName);
        }
    }

    /**
     * Downloads and processes given file, collecting ask and bid values to lists 
     * @param xmlFileName name of xml file to parse
     * @throws java.lang.Exception when there is a problem with connection to NBP server
     */
    public void parseFile(String xmlFileName) throws Exception {
        AskBidEntry askBid = getAskBidEntry(xmlFileName);
        if (askBid != null) {
            parsedAsksList.add(askBid.getAsk());
            parsedBidsList.add(askBid.getBid());
        }
    }

    /**
     * Downloads file and extracts ask and bid values
     * @param xmlFile name of xml file
     * @return AskBidEntry containing ask and bid values for set currency from given file
     * @throws java.lang.Exception when there is a problem with connection to NBP server 
     */
    private AskBidEntry getAskBidEntry(String xmlFile) throws Exception {
        try {
            URL url = new URL(XML_FILE_PATH + xmlFile + XML_FILE_EXTENSION);

            try (Scanner scanner = new Scanner(url.openStream(), XML_FILE_ENCODING)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains(CURRENCY_TAG) && currency.equalsIgnoreCase(getTagValueInLine(CURRENCY_TAG, line))) {
                        Double ask = getDoubleValueInLine(ASK_TAG, scanner.nextLine());
                        Double bid = getDoubleValueInLine(BID_TAG, scanner.nextLine());

                        return new AskBidEntry(ask, bid);
                    }
                }
            } catch (IOException ex) {
                throw new Exception("There was a problem with connection to NBP server");
            }
        } catch (MalformedURLException ex) {
            throw new Exception("There was a problem with connection to NBP server");
        }
        return null;
    }

    private String getOpeningTag(String tag) {
        return "<" + tag + ">";
    }

    private String getClosingTag(String tag) {
        return "</" + tag + ">";
    }

    /**
     * Finds tag in given line and gets its value
     * @param tag xml tag
     * @param line line containing tag
     * @return string value enclosed in tag
     */
    private String getTagValueInLine(String tag, String line) {
        String opening = getOpeningTag(tag);
        String closing = getClosingTag(tag);
        int openingEndPostition = line.indexOf(opening) + opening.length();
        int closingPosition = line.indexOf(closing);
        return line.substring(openingEndPostition, closingPosition);
    }

    /**
     * Finds tag in given line and gets its double value
     * @param tag xml tag
     * @param line line containing tag
     * @return double value enclosed in tag
     */
    private Double getDoubleValueInLine(String tag, String line) {
        String stringValue = getTagValueInLine(tag, line);
        stringValue = stringValue.replace(",", ".");
        return Double.valueOf(stringValue);
    }
}
