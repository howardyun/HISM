package com.HISM.backfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipOffUser {
    String userId;
    String informerId;
    Date tipOffTime;
    String tipOffContent;
    int isValid;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInformerId() {
        return informerId;
    }

    public void setInformerId(String informerId) {
        this.informerId = informerId;
    }

    public Date getTipOffTime() {
        return tipOffTime;
    }

    public void setTipOffTime(Date tipOffTime) {
        this.tipOffTime = tipOffTime;
    }

    public String getTipOffContent() {
        return tipOffContent;
    }

    public void setTipOffContent(String tipOffContent) {
        this.tipOffContent = tipOffContent;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
