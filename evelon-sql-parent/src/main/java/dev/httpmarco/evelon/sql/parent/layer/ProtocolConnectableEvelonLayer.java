package dev.httpmarco.evelon.sql.parent.layer;

import dev.httpmarco.evelon.common.credentials.Credentials;
import dev.httpmarco.evelon.common.layers.ConnectableEvelonLayer;

import java.sql.Connection;

public interface ProtocolConnectableEvelonLayer extends ConnectableEvelonLayer<Object, Credentials, Connection> {

    ProtocolDriver protocolDriver();

}