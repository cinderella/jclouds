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

package org.jclouds.abiquo.predicates.network;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import org.jclouds.abiquo.domain.network.AbstractPublicIp;
import org.jclouds.abiquo.domain.network.Ip;

import com.google.common.base.Predicate;

/**
 * Container for {@link Network} filters.
 * 
 * @author Francesc Montserrat
 */
public class IpPredicates {
   public static <T extends Ip<?, ?>> Predicate<T> name(final String... names) {
      checkNotNull(names, "names must be defined");

      return new Predicate<T>() {
         @Override
         public boolean apply(final T address) {
            return Arrays.asList(names).contains(address.getName());
         }
      };
   }

   public static <T extends Ip<?, ?>> Predicate<T> address(final String... addresses) {
      checkNotNull(addresses, "addresses must be defined");

      return new Predicate<T>() {
         @Override
         public boolean apply(final T address) {
            return Arrays.asList(addresses).contains(address.getIp());
         }
      };
   }

   public static <T extends AbstractPublicIp<?, ?>> Predicate<T> available() {
      return new Predicate<T>() {
         @Override
         public boolean apply(final T address) {
            return address.isAvailable();
         }
      };
   }

   public static <T extends Ip<?, ?>> Predicate<T> notUsed() {
      return new Predicate<T>() {
         @Override
         public boolean apply(final T address) {
            return address.unwrap().searchLink("virtualmachine") == null;
         }
      };
   }
}
