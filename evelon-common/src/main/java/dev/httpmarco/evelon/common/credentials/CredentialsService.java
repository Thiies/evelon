package dev.httpmarco.evelon.common.credentials;

import dev.httpmarco.evelon.common.layers.ConnectableEvelonLayer;
import dev.httpmarco.osgon.files.json.JsonDocument;

import java.nio.file.Path;

public final class CredentialsService {

    private final JsonDocument<CredentialsConfiguration> config = new JsonDocument<>(new CredentialsConfiguration(), Path.of("credentials.json"), new CredentialsTypeAdapter());

    public void addCredentials(ConnectableEvelonLayer<?, ?, ?> layer) {
        if (config.value().credentials().stream().noneMatch(it -> it.id().equals(layer.templateCredentials().id()))) {
            this.config.append(list -> list.credentials().add(layer.templateCredentials()));
        }
    }
}
