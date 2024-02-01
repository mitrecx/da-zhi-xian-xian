package top.mitrecx.dazhixianxian.controller;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mitrecx.dazhixianxian.common.dataformat.ObjectMappers;
import top.mitrecx.dazhixianxian.config.BuildConfig;

@RestController
@RequestMapping("/v1/about")
public class AboutController {
    private final long startedAt = System.currentTimeMillis();
    @Autowired
    private BuildConfig buildConfig;

    @GetMapping
    public ObjectNode about() {
        ObjectMapper om = ObjectMappers.get();
        ObjectNode version = om.createObjectNode();
        version.put("name", buildConfig.getProject());
        version.put("version", buildConfig.getVersion());
        version.put("buildNumber", buildConfig.getBuildNumber());
        version.put("branch", buildConfig.getBranch());
        version.put("buildAt",
                DateFormatUtils.format(startedAt, "yyyy-MM-dd HH:mm:ss"));

        ObjectNode r = om.createObjectNode();
        r.set("version", version);
        r.put("upTime",
                DateUtil.formatBetween(System.currentTimeMillis() - startedAt));
        return r;
    }
}
