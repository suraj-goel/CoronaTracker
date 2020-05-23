package data;

import java.io.Serializable;
import java.util.HashMap;

public class CovidGeneral implements Serializable {
    private long totalCases;
    private long confirmedIndians;
    private long foreigners;
    private long discharged;
    private long deaths;
    public HashMap<String,Long> mp;

    public CovidGeneral() {
        mp = new HashMap<>();
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public long getConfirmedIndians() {
        return confirmedIndians;
    }

    public void setConfirmedIndians(long confirmedIndians) {
        this.confirmedIndians = confirmedIndians;
    }

    public long getForeigners() {
        return foreigners;
    }

    public void setForeigners(long foreigners) {
        this.foreigners = foreigners;
    }

    public long getDischarged() {
        return discharged;
    }

    public void setDischarged(long discharged) {
        this.discharged = discharged;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }
}
