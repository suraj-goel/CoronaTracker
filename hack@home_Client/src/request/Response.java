package request;



import java.io.Serializable;

public class Response implements Serializable {
    String responseID;
    Object responseObject;
    String responseCode;

    public Response(String responseID, Object responseObject, String responseCode) {
        this.responseID = responseID;
        this.responseObject = responseObject;
        this.responseCode = responseCode;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
