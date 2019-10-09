package com.mazzama.research.multisourcedb.exception;

public class TenantException extends RuntimeException {

    public TenantException() {
        super();
    }

    public TenantException(String message) {
        super(message);
    }

    public TenantException(String message, Throwable cause) {
        super(message, cause);
    }
}
