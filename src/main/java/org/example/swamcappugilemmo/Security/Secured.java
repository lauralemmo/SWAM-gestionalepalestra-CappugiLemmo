package org.example.swamcappugilemmo.Security;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    // Questa è solo un'etichetta vuota, non fa nulla da sola!
}