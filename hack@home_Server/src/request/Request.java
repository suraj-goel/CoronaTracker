
package request;



import java.io.Serializable;

public abstract class Request implements Serializable {
    public abstract String getRequestCode();
}