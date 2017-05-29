package pl.parser.nbp.files;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pl.parser.nbp.utils.DateUtils;

/**
 * Reads dir files and creates list of required xml files to parse
 * @author Michał Lal
 */
public class FileListReader {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<String> dirFiles;
    private final List<String> xmlFiles;
    public static final String XML_FILE_PATTERN = "c\\d{3}z\\d{6}";
    public static final String XML_FILE_DATE_PATTERN = "yyMMdd";
    public static final String DIR_FILE_PATH = "http://www.nbp.pl/kursy/xml/";
    public static final String DIR_FILE_EXTENSION = ".txt";
    public static final String DIR_FILE_ENCODING = "UTF-8";
    
    public FileListReader(LocalDate startDate, LocalDate endDate) {
        dirFiles = new ArrayList<>();
        xmlFiles = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * Read dir files and prepare list of xml files to download
     * @throws java.lang.Exception when there is a problem with connection to NBP server
     */
    public void readFiles() throws Exception{
        dirFiles.addAll(getDirsToDownload());
        for (String dirFile : dirFiles) {
            xmlFiles.addAll(getFilesToDownload(dirFile));
        }
    }

    public List<String> getXmlFiles() {
        return xmlFiles;
    }
    
    /**
     * Prepare list of dir files to read xml files from
     * @return list of dir files names for given date range
     */
    private List<String> getDirsToDownload() {
        List<String> dirs = new ArrayList<>();
        for (int year = startDate.getYear(); year < endDate.getYear(); year++) {
            dirs.add("dir" + year);
        }

        if (endDate.getYear() == LocalDate.now().getYear()) {
            dirs.add("dir");
        } else {
            dirs.add("dir" + endDate.getYear());
        }

        return dirs;
    }

    /**
     * Prepare list of xml files to read data from
     * @param dirFile dir file containing list of xml files
     * @return list of all xml files names from given dirFile in required date range
     * @throws java.lang.Exception when there is a problem with connection to NBP server 
     */
    private List<String> getFilesToDownload(String dirFile) throws Exception {
        List<String> files = new ArrayList<>();
        try {
            URL dirUrl = new URL(DIR_FILE_PATH + dirFile + DIR_FILE_EXTENSION);

            try (Scanner scanner = new Scanner(dirUrl.openStream(), DIR_FILE_ENCODING)) {
                scanner.findWithinHorizon("\uFEFF", 1); //ommit BOM (byte order mark)
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.matches(XML_FILE_PATTERN)) {
                        LocalDate date = LocalDate.parse(line.substring(5, 11), DateTimeFormatter.ofPattern(XML_FILE_DATE_PATTERN));
                        if (DateUtils.isInDateRange(date, startDate, endDate)) {
                            files.add(line);
                        }
                    }
                }
            } catch (IOException ex) {
                throw new Exception("There was a problem with connection to NBP server");
            }
        } catch (MalformedURLException ex) {
            throw new Exception("There was a problem with connection to NBP server");
        }
        return files;
    }
    
}
