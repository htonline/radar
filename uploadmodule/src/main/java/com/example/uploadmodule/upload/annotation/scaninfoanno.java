package com.example.uploadmodule.upload.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface scaninfoanno {
    String value();
}
