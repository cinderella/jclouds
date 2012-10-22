/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.vcloud.director.v1_5.login;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;

import org.jclouds.rest.annotations.*;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType;
import org.jclouds.vcloud.director.v1_5.binders.BindUserOrgAndPasswordAsBasicAuthorizationHeader;
import org.jclouds.vcloud.director.v1_5.domain.Session;
import org.jclouds.vcloud.director.v1_5.domain.SessionWithToken;
import org.jclouds.vcloud.director.v1_5.filters.AddVCloudAuthorizationAndCookieToRequest;
import org.jclouds.vcloud.director.v1_5.filters.AddVCloudVersionToRequest;
import org.jclouds.vcloud.director.v1_5.parsers.SessionWithTokenFromXMLAndHeader;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorConstants;

/**
 * Provides asynchronous access to Session via their REST API.
 * <p/>
 * 
 * @see SessionApi
 * @author Adrian Cole
 */
@RequestFilters(AddVCloudVersionToRequest.class)
public interface SessionAsyncApi {

   /**
    * @see SessionApi#loginUserInOrgWithPassword
    */
   @POST
   @Consumes
   @ResponseParser(SessionWithTokenFromXMLAndHeader.class)
   @MapBinder(BindUserOrgAndPasswordAsBasicAuthorizationHeader.class)
   ListenableFuture<SessionWithToken> loginUserInOrgWithPassword(@EndpointParam URI loginUrl,
            @PayloadParam("user") String user, @PayloadParam("org") String org,
            @PayloadParam("password") String password);

   /**
    * @see SessionApi#getSessionWithToken
    */
   @GET
   @Consumes
   @JAXBResponseParser
   ListenableFuture<Session> getSessionWithToken(@EndpointParam URI session,
            @HeaderParam("x-vcloud-authorization") String authenticationToken);

   /**
    * @see SessionApi#logoutSessionWithToken
    */
   @DELETE
   @Consumes
   @JAXBResponseParser
   ListenableFuture<Void> logoutSessionWithToken(@EndpointParam URI session,
            @HeaderParam("x-vcloud-authorization") String authenticationToken);
}
