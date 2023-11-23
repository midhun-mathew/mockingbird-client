package com.incredibles;

public class Config {
    private final boolean enabled;
    private final String mockServerUrl;

    public Config(boolean enabled, String mockServerUrl) {
        this.enabled = enabled;
        this.mockServerUrl = mockServerUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getMockServerUrl() {
        return mockServerUrl;
    }
}
