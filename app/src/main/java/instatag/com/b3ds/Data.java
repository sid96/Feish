
package instatag.com.b3ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_type")
    @Expose
    private String userType;

    public String getId() {
        return id;
    }


    public String getUserType() {
        return userType;
    }


}
