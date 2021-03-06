/*
 * Copyright 2013-2014 the original author or authors.
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

import org.springframework.batch.admin.domain.JobInfo;
import org.springframework.batch.admin.domain.JobInfoResource;
import org.springframework.batch.admin.web.BatchJobsController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * Knows how to build a REST resource out of our domain model
 * {@link org.springframework.batch.admin.domain.JobInfo}.
 *
 * @author Ilayaperumal Gopinathan
 */
public class JobInfoResourceAssembler extends RepresentationModelAssemblerSupport<JobInfo, JobInfoResource> {

    public JobInfoResourceAssembler() {
        super(BatchJobsController.class, JobInfoResource.class);
    }

    @Override
    public JobInfoResource toModel(JobInfo entity) {
        return createModelWithId(entity.getName(), entity);
    }

    @Override
    protected JobInfoResource instantiateModel(JobInfo entity) {
        return new JobInfoResource(entity.getName(), entity.getExecutionCount(), entity.getJobInstanceId(),
                entity.isLaunchable(), entity.isIncrementable());
    }
}
