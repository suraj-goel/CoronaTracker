package request;

import java.io.Serializable;

public class CovidStats extends Request implements Serializable {

    @Override
    public String getRequestCode() {
        return "covidgeneral";
    }
}
