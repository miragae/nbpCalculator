package pl.parser.nbp.data;

/**
 * Contains information about ask and bid values
 * @author Michał Lal
 */
public class AskBidEntry {

    private final Double ask;
    private final Double bid;

    public AskBidEntry(Double ask, Double bid) {
        this.ask = ask;
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public Double getBid() {
        return bid;
    }
}
