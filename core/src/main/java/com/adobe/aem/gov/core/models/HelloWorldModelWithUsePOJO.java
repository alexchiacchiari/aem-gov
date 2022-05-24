/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.gov.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldModelWithUsePOJO extends WCMUsePojo {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldModelWithUsePOJO.class);
    private String message;

    @Override
    public void activate(){
        String currentPagePath = getResourcePage().getPath();

        message = "Current page is:  " + currentPagePath + "\n";


        LOG.info(getWcmMode().toString());
        LOG.info(getProperties().toString());
        LOG.info(getPageProperties().toString());
    }

    public String getMessage() {
        return message;
    }
}
