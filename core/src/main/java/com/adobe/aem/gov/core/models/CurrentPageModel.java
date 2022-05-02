package com.adobe.aem.gov.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import javax.jcr.Node;

@Model(adaptables = {Page.class})
public class CurrentPageModel{

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private Node currentNode;

    public Page getCurrentPage() {
        return this.currentPage;
    }

    public Node getCurrentNode() {
        return this.currentNode;
    }
}
