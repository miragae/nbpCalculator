package pl.parser.nbp;

import pl.parser.nbp.files.FileListReader;
import pl.parser.nbp.files.XmlParser;
import pl.parser.nbp.validation.Validator;
import pl.parser.nbp.validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import pl.parser.nbp.utils.MathUtils;

/**
 * Calculates measures giving informations about currency value using data from NBP server
 * @author Michał Lal
 */
public class NbpCalculator {

    private final String currency;
    private final LocalDate startDate;
    private final LocalDate endDate;
    public static final DateTimeFormatter ARG_DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    public static final LocalDate MINIMUM_DATE = LocalDate.of(2012, 1, 1);
    public static final String CURRENCY_PATTERN = "\\w{3}";

    /**
     * NBP Calculator constructor
     * @param currency 3 letter currency code
     * @param startDateString start date of calculation range
     * @param endDateString end date of calculation range
     * @throws ValidationException when parameters are invalid
     */
    public NbpCalculator(String currency, String startDateString, String endDateString) throws ValidationException {
        Validator.validateDateStrings(startDateString, endDateString, ARG_DATE_FORMAT, MINIMUM_DATE);
        Validator.validateCurrency(currency, CURRENCY_PATTERN);

        this.startDate = LocalDate.parse(startDateString, ARG_DATE_FORMAT);
        this.endDate = LocalDate.parse(endDateString, ARG_DATE_FORMAT);
        this.currency = currency;
    }

    /**
     * Calculates and prints mean of ask values and standard deviation of bid values of currency in given range
     * @throws Exception when there was a problem with processing data
     */
    public void calculate() throws Exception {
        FileListReader fileListReader = new FileListReader(startDate, endDate);
        fileListReader.readFiles();
        
        List<String> xmlFiles = fileListReader.getXmlFiles();

        XmlParser xmlParser = new XmlParser(currency);
        xmlParser.parseFiles(xmlFiles);
        
        List<Double> askList = xmlParser.getParsedAsksList();
        List<Double> bidList = xmlParser.getParsedBidsList();
        
        double askMean = MathUtils.calculateMean(askList);
        double bidStdDev = MathUtils.calculateStandardDeviation(bidList);
        
        System.out.println(String.format("%.4f", askMean));
        System.out.println(String.format("%.4f", bidStdDev));
    }
}
