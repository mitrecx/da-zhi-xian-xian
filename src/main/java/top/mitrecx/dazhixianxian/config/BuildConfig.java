package top.mitrecx.dazhixianxian.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:build.properties"})
@ConfigurationProperties(prefix = "build")
public class BuildConfig {
    private String project;
    private String version;
    private String buildNumber;
    private String timestamp;
    private String branch;

    public String getProject() {
        return project;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBranch() {
        return branch;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
