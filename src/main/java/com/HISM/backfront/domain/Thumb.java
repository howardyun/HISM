package com.HISM.backfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Thumb {
    // 被点赞的id
    int dynamicId;

    // 点赞的用户id
    String userId;

    public Thumb() {
    }

    public Thumb(int dynamicId, String userId) {
        this.dynamicId = dynamicId;
        this.userId = userId;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
