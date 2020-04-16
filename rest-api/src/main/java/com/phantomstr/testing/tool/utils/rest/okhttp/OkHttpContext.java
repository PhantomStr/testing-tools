package com.phantomstr.testing.tool.utils.rest.okhttp;

import java.io.InputStream;

public interface OkHttpContext {
    default InputStream getCertificate() {
        return null;
    }

}
