/*
 * blackduck-installer
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.blackduck.installer.model

import com.synopsys.integration.blackduck.installer.exception.BlackDuckInstallerException
import org.apache.commons.lang3.StringUtils

class CustomCertificate {
    String customCertPath
    String customKeyPath

    CustomCertificate(String customCertPath, String customKeyPath) {
        this.customCertPath = customCertPath
        this.customKeyPath = customKeyPath

        String[] values = [customCertPath, customKeyPath]
        if (StringUtils.isAnyBlank(values) && !StringUtils.isAllBlank(values)) {
            throw new BlackDuckInstallerException("For blackduck.install.custom.cert.path and blackduck.install.custom.key.path, either both should be set, or neither should be set.")
        }
    }

    boolean isEmpty() {
        StringUtils.isAllBlank(customCertPath, customKeyPath)
    }

}