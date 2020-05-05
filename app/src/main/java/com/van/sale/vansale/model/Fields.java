package com.van.sale.vansale.model;

import java.util.List;

/**
 * Created by maaz on 24/09/18.
 */

public class Fields {

private List<String> fields;

    public Fields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
