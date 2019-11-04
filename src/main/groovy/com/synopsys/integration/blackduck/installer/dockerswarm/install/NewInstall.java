/**
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
package com.synopsys.integration.blackduck.installer.dockerswarm.install;

import com.synopsys.integration.blackduck.installer.dockerswarm.DockerCommands;
import com.synopsys.integration.blackduck.installer.model.AlertInstallOptions;
import com.synopsys.integration.blackduck.installer.model.CustomCertificate;
import com.synopsys.integration.executable.Executable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewInstall implements InstallMethod {
    private final DockerCommands dockerCommands;
    private final String stackName;
    private final CustomCertificate customCertificate;
    private final boolean useLocalOverrides;
    private AlertInstallOptions alertInstallOptions;

    public NewInstall(DockerCommands dockerCommands, String stackName, CustomCertificate customCertificate, boolean useLocalOverrides, boolean installAlert) {
        this.dockerCommands = dockerCommands;
        this.stackName = stackName;
        this.customCertificate = customCertificate;
        this.useLocalOverrides = useLocalOverrides;
    }

    @Override
    public boolean shouldPerformInstall() {
        return true;
    }

    public List<Executable> createExecutables(File installDirectory) {
        List<Executable> executables = new ArrayList<>();

        if (!customCertificate.isEmpty()) {
            executables.add(dockerCommands.createSecretCert(stackName, customCertificate.getCustomCertPath()));
            executables.add(dockerCommands.createSecretKey(stackName, customCertificate.getCustomKeyPath()));
        }

        if (useLocalOverrides) {
            executables.add(dockerCommands.startStackWithLocalOverrides(installDirectory, stackName));
        } else {
            executables.add(dockerCommands.startStack(installDirectory, stackName));
        }

        return executables;
    }

}