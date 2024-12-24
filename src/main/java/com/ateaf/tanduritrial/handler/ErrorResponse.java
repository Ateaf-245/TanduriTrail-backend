package com.ateaf.tanduritrial.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
