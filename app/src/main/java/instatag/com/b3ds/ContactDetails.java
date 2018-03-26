
package instatag.com.b3ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContactDetails implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DetailsData data;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailsData getData() {
        return data;
    }

    public void setData(DetailsData data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}