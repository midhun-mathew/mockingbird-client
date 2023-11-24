package com.incredibles;

public class MockingbirdConfig {
    private final boolean enabled;
    private final String mockServerUrl;
    private final String applicationName;

    public MockingbirdConfig(boolean enabled, String mockServerUrl, String applicationName) {
        this.enabled = enabled;
        this.mockServerUrl = mockServerUrl;
        this.applicationName = applicationName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getMockServerUrl() {
        return mockServerUrl;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
