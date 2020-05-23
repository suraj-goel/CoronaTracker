package data;

import java.io.Serializable;
import java.util.ArrayList;

public class CovidNews implements Serializable {
    public ArrayList<String>title,body;

    public CovidNews() {
        this.title = new ArrayList<>();
        this.body = new ArrayList<>();

    }
}
