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
package com.synopsys.integration.blackduck.installer.workflow;

import com.synopsys.integration.blackduck.installer.dockerswarm.DockerCommands;
import com.synopsys.integration.blackduck.installer.dockerswarm.NoInstall;
import com.synopsys.integration.blackduck.installer.dockerswarm.alertinstall.NewInstall;
import com.synopsys.integration.blackduck.installer.model.AlertEncryption;
import com.synopsys.integration.blackduck.installer.model.InstallMethod;

public class AlertInstallMethodDecider {
    private InstallMethod installMethod;
    private DockerCommands dockerCommands;
    private String stackName;
    private AlertEncryption alertEncryption;

    public AlertInstallMethodDecider(InstallMethod installMethod, DockerCommands dockerCommands, String stackName, AlertEncryption alertEncryption) {
        this.installMethod = installMethod;
        this.dockerCommands = dockerCommands;
        this.stackName = stackName;
        this.alertEncryption = alertEncryption;
    }

    public com.synopsys.integration.blackduck.installer.dockerswarm.InstallMethod determineInstallMethod() {
        if (InstallMethod.NEW == installMethod) {
            return getNewInstall();
        } else {
            return new NoInstall();
        }
    }

    private NewInstall getNewInstall() {
        return new NewInstall(dockerCommands, stackName, alertEncryption);
    }

}