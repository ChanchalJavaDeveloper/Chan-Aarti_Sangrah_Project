package com.AartiSangrah.Data;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class clsParamPass extends StringRequest {
    Map<String, String> parameters;
    String[] arField, arValues;
    int n, i;

    public clsParamPass(String APIUrl, String tFieldList, String tValueList, Response.Listener<String> listener) {
        super(Method.POST, APIUrl, listener, null);
        parameters = new HashMap<>();
        arField = tFieldList.split("~");
        arValues = tValueList.split("~");

        n = arField.length;
        for (i = 0; i < n; i++) {
            parameters.put(arField[i], arValues[i]);
        }
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}

