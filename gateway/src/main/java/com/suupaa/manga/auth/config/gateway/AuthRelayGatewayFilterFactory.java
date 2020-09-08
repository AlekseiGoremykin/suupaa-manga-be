package com.suupaa.manga.auth.config.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.suupaa.manga.identity.IdentityConstant;

import reactor.core.publisher.Mono;

@Component
public class AuthRelayGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private final ServerOAuth2AuthorizedClientRepository authorizedClientRepository;

    public AuthRelayGatewayFilterFactory(ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
        super(Object.class);
        this.authorizedClientRepository = authorizedClientRepository;
    }

    public GatewayFilter apply() {
        return apply((Object) null);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> exchange.getPrincipal()
                .filter(principal -> principal instanceof OAuth2AuthenticationToken)
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(this::authorizedClient)
                .map(token -> withAuthPrincipal(exchange, token))
                .defaultIfEmpty(exchange).flatMap(chain::filter);
    }

    private Mono<String> authorizedClient(OAuth2AuthenticationToken oauth2Authentication) {
        if (oauth2Authentication.getPrincipal() instanceof OidcUser) {
            return Mono.just(((OidcUser) oauth2Authentication.getPrincipal()).getUserInfo().getEmail());
        }
        return Mono.just(oauth2Authentication.getPrincipal().getName());
    }

    private ServerWebExchange withAuthPrincipal(ServerWebExchange exchange,
                                                String userId) {
        return exchange.mutate()
                .request(r -> r.headers(headers -> headers.set(IdentityConstant.IDENTITY_HEADER, userId)))
                .build();
    }
}
