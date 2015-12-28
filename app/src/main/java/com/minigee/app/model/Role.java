package com.minigee.app.model;

import com.minigee.app.base.BaseModel;

/**
 * Created by Zhou on 2015-12-17.
 */
public class Role extends BaseModel {
    private String model;
    private String title;
    private String rules;
    private String status;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }


}
