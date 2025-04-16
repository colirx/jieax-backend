package com.colirx.jieax.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultData<T> {

    private String code;
    private String message;
    private T data;
    private Long timestamp;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(ResultCode.RC_200.getCode());
        resultData.setMessage(ResultCode.RC_200.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> failure(ResultCode resultCode) {
        return failure(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> ResultData<T> failure(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);
        return resultData;
    }
}
