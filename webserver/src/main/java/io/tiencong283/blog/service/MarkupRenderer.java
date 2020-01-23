package io.tiencong283.blog.service;

import io.tiencong283.blog.model.PostFormat;
import io.tiencong283.blog.support.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MarkupRenderer {
    private ServiceClient serviceClient;

    @Autowired
    public void setServiceClient(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    public String render(String content, PostFormat format) {
        String rendered = "";
        content = StringUtils.trimWhitespace(content);
        if (StringUtils.isEmpty(content))
            return "";
        if (format == PostFormat.MARKDOWN) {
            rendered = serviceClient.renderMarkdown(content);
        }
        return rendered;
    }
}
