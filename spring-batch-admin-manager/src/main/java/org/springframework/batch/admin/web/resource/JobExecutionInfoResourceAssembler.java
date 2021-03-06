/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.batch.admin.web.resource;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.batch.admin.domain.JobExecutionInfo;
import org.springframework.batch.admin.domain.JobExecutionInfoResource;
import org.springframework.batch.admin.domain.StepExecutionInfo;
import org.springframework.batch.admin.domain.StepExecutionInfoResource;
import org.springframework.batch.admin.web.BatchJobExecutionsController;
import org.springframework.batch.core.StepExecution;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * Knows how to build a REST resource out of our domain model
 * {@link org.springframework.batch.admin.domain.JobExecutionInfo}.
 * 
 * @author Ilayaperumal Gopinathan
 */
public class JobExecutionInfoResourceAssembler extends
        RepresentationModelAssemblerSupport<JobExecutionInfo, JobExecutionInfoResource> {

    private StepExecutionInfoResourceAssembler stepExecutionInfoResourceAssembler = new StepExecutionInfoResourceAssembler();

    public JobExecutionInfoResourceAssembler() {
        super(BatchJobExecutionsController.class, JobExecutionInfoResource.class);
    }

    @Override
    public JobExecutionInfoResource toModel(JobExecutionInfo entity) {
        return createModelWithId(entity.getJobExecution().getId(), entity);
    }

    @Override
    protected JobExecutionInfoResource instantiateModel(JobExecutionInfo entity) {
        Collection<StepExecutionInfoResource> stepExecutionInfoResources = new ArrayList<StepExecutionInfoResource>(
                entity.getStepExecutionCount());

        if (entity.getStepExecutionCount() > 0) {
            for (StepExecution stepExecution : entity.getJobExecution().getStepExecutions()) {
                stepExecutionInfoResources.add(
                        stepExecutionInfoResourceAssembler
                                .toModel(new StepExecutionInfo(stepExecution, entity.getTimeZone())));
            }
        }

        JobExecutionInfoResource jobExecutionInfoResource = new JobExecutionInfoResource(entity.getJobExecution(),
                entity.getTimeZone());

        jobExecutionInfoResource.setStepExecutions(stepExecutionInfoResources);
        return jobExecutionInfoResource;
    }
}
