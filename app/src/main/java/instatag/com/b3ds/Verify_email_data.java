package instatag.com.b3ds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verify_email_data {

    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}