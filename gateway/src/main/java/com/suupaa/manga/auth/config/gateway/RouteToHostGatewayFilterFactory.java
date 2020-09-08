package com.suupaa.manga.auth.config.gateway;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import java.net.URI;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RouteToHostGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            URI uri = exchange.getRequest().getURI();
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

            final String serviceName = exchange.getRequest()
                    .getPath()
                    .pathWithinApplication()
                    .elements()
                    .get(1)
                    .value();

            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .host(serviceName)
                    .port(80)
                    .build()
                    .toUri();

            final Route newRoute = Route.async()
                    .predicate(serverWebExchange -> true)
                    .id(route.getId())
                    .uri(newUri)
                    .order(route.getOrder())
                    .filters(route.getFilters())
                    .build();

            exchange.getAttributes().put(GATEWAY_ROUTE_ATTR, newRoute);

            return chain.filter(exchange);
        };
    }
}
