package com.adobe.aem.gov.core.models;


import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class,
       defaultInjectionStrategy= DefaultInjectionStrategy.OPTIONAL)
public class Menu {

    @ValueMapValue(name = "menuvoices")
    List<String> menuvoices;

    @SlingObject
    ResourceResolver resourceResolver;

    public boolean isEmpty() {
        return menuvoices == null;
    }

    public List<String> getMenuVoices() {
        if (menuvoices != null) {
            return new ArrayList<>(menuvoices);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Page> getPages() {
        ArrayList<Page> pages = new ArrayList<>();
        if (menuvoices != null) {
            for (String path : menuvoices) {
                Page itemPages = resourceResolver
                        .resolve(path)
                        .adaptTo(Page.class);
                pages.add(itemPages);
            }
            return pages;
        } else {
            return Collections.emptyList();
        }
    }
}