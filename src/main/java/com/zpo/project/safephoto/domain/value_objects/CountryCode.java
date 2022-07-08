package com.zpo.project.safephoto.domain.value_objects;

public class CountryCode {
    public String code;

    public static CountryCode create(String code) throws Exception {
        if (code.length() != 2) {
            throw new Exception();
        } else {
            return new CountryCode(code);
        }
    }

    public CountryCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
