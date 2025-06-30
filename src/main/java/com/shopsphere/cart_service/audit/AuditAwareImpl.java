package com.shopsphere.cart_service.audit;

import com.shopsphere.cart_service.context.UserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(UserContext.get())
                .or(() -> Optional.of("CARTS_MS"));
    }
}
