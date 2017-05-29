package pl.parser.nbp;

/**
 *
 * @author Michał Lal
 */
public class MainClass {

    public static void main(String[] args){
        if (args.length != 3) {
            System.out.println("Wrong number of arguments. Enter 3 arguments separated with spaces: \n"
                    + "1. three letter currency code\n"
                    + "2. start date (yyyy-mm-dd)\n"
                    + "3. end date (yyyy-mm-dd)");
            return;
        }

        try {
            NbpCalculator nbpParser = new NbpCalculator(args[0], args[1], args[2]);
            nbpParser.calculate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
