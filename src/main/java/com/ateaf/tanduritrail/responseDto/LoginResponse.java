package com.ateaf.tanduritrail.responseDto;

import java.util.List;

public record LoginResponse(
        String token,
        String username,
        List<String> role
){}
