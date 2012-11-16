/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.vcloud.director.functions;

import com.google.common.base.Function;
import org.jclouds.rest.annotations.ApiVersion;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class AppendApiVersionToVCloudMimeType implements Function<String, String>
{
    private static final String VCLOUD_DIRECTOR_MIME_TYPE_PREFIX = "application/*+xml";

    protected String apiVersion;

    @Inject
    public AppendApiVersionToVCloudMimeType(@ApiVersion final String apiVersion)
    {
        super();
        this.apiVersion = checkNotNull(apiVersion, "apiVersion");
    }

    @Override
    public String apply(final String input)
    {

        MediaType mediaType = MediaType.valueOf(checkNotNull(input, "input"));
        if (!mediaType.getParameters().containsKey("version"))
        {
            return mediaType.toString() + ";version=" + apiVersion;
        }
        else
        {
            return mediaType.toString();
        }
    }
}
