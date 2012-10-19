package org.jclouds.vcloud.director.v1_5.filters;

import com.google.common.collect.ImmutableMultimap;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;

/**
 * @author Shane Witbeck
 * @since 10/19/12
 */
public class AddVCloudVersionToRequest implements HttpRequestFilter {

    @Override
    public HttpRequest filter(HttpRequest request) throws HttpException {
        return request
                .toBuilder()
                .replaceHeaders(
                        ImmutableMultimap.of(
                                "Accept", "*/*;version=1.5"
                        )
                ).build();
    }

}
