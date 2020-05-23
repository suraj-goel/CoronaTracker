package request;

import java.io.Serializable;

public class CovidNewsRequest extends Request implements Serializable {
    private String country,source;

    public String getCountry() {
        return country;
    }

    public String getSource() {
        return source;
    }

    public CovidNewsRequest(String country, String source) {
        this.country = country;
        this.source = source;
    }

    @Override
    public String getRequestCode() {
        return "covidnews";
    }
}
