package dev.httpmarco.evelon.sql.h2;

import dev.httpmarco.evelon.layer.connection.ConnectionAuthentication;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class H2ConnectionAuthentication extends ConnectionAuthentication {

    private final String path;

    public H2ConnectionAuthentication() {
        super("H2", false);

        this.path = "database.h2";
    }
}
