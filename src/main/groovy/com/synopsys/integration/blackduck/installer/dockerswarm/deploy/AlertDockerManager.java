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
package com.synopsys.integration.blackduck.installer.dockerswarm.deploy;

import com.synopsys.integration.blackduck.installer.dockerswarm.DockerCommands;
import com.synopsys.integration.blackduck.installer.dockerswarm.output.DockerSecrets;
import com.synopsys.integration.blackduck.installer.dockerswarm.output.DockerServices;
import com.synopsys.integration.blackduck.installer.dockerswarm.output.DockerStacks;
import com.synopsys.integration.blackduck.installer.model.AlertEncryption;
import com.synopsys.integration.blackduck.installer.model.DockerService;
import com.synopsys.integration.executable.Executable;
import com.synopsys.integration.log.IntLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlertDockerManager extends ProductDockerManager {
    public static final String ALERT_SERVICE_NAME = "alert";

    private final AlertEncryption alertEncryption;
    private final DockerService alertService;

    public AlertDockerManager(IntLogger logger, DockerCommands dockerCommands, String stackName, AlertEncryption alertEncryption, DockerService alertService) {
        super(logger, dockerCommands, stackName);
        this.alertEncryption = alertEncryption;
        this.alertService = alertService;
    }

    public List<Executable> createExecutables(File installDirectory, DockerStacks dockerStacks, DockerSecrets dockerSecrets, DockerServices dockerServices) {
        List<Executable> executables = new ArrayList<>();

        if (dockerServices.doesServiceExist(alertService)) {
            logger.info(String.format("Removing the service \"%s\".", alertService.getDockerName()));
            executables.add(dockerCommands.removeService(alertService));
        }

        if (!alertEncryption.isEmpty()) {
            addSecret(executables, dockerSecrets, alertEncryption.getPassword());
            addSecret(executables, dockerSecrets, alertEncryption.getSalt());
        }

        return executables;
    }

}
