package com.bigbook.app.auth.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class RedirectResponseDto {
    String redirectUrl;
}
