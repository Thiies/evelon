package dev.httpmarco.evelon.sql.parent.layer;

import dev.httpmarco.evelon.common.Evelon;
import dev.httpmarco.evelon.common.builder.BuilderType;
import dev.httpmarco.evelon.common.credentials.Credentials;
import dev.httpmarco.evelon.common.filters.LayerFilterHandler;
import dev.httpmarco.evelon.common.layers.ConnectableEvelonLayer;
import dev.httpmarco.evelon.common.model.SubStage;
import dev.httpmarco.evelon.common.query.intern.DataQuery;
import dev.httpmarco.evelon.common.query.SortedOrder;
import dev.httpmarco.evelon.common.query.response.QueryResponse;
import dev.httpmarco.evelon.common.repository.InitializeRepository;
import dev.httpmarco.evelon.common.repository.Repository;
import dev.httpmarco.evelon.sql.parent.builder.SqlQueryBuilder;
import dev.httpmarco.evelon.sql.parent.connection.HikariConnection;
import dev.httpmarco.evelon.sql.parent.model.SqlModel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;

@Getter
@Accessors(fluent = true)
public abstract class SqlParentConnectionLayer implements ConnectableEvelonLayer<Object, Credentials, Connection>, InitializeRepository {

    private final String id;
    private boolean active = false;
    private final SqlModel model = new SqlModel();
    private final HikariConnection connection;

    public SqlParentConnectionLayer(String id, ProtocolDriver<? extends Credentials> driver) {
        this.id = id;
        this.connection = new HikariConnection(driver);
    }

    @Override
    public void initialize() {
        this.active = true;
        this.connection.connect(Evelon.instance().credentialsService().credentials(this));
    }

    @Override
    public void close() {
        this.connection.close();
    }

    @Override
    public <T> void initializeRepository(Repository<T> repository) {
        var builder = SqlQueryBuilder.emptyInstance(repository.name(), model, BuilderType.INITIALIZE);
        repository.clazz().stageOf(model).asSubStage().initialize(repository, repository.name(), this.model, null, repository.clazz().asObjectClass(), builder);
        builder.push(connection).close();
    }

    @Override
    public QueryResponse create(DataQuery<Object> query, Object value) {
        var response = new QueryResponse();
        var builder = SqlQueryBuilder.emptyInstance(query.repository().name(), model, BuilderType.CREATION);

        var stage = query.repository().clazz().stageOf(model).asSubStage();
        stage.create(value, query.repository().name(), this.model, null, query.repository().clazz().asObjectClass(), builder);

        response.append(builder.push(connection));
        return response.close();
    }

    @Override
    public void createIfNotExists(DataQuery<Object> query, Object value) {
        //todo
    }

    @Override
    public QueryResponse deleteAll(DataQuery<Object> query) {
        var response = new QueryResponse();
        var builder = SqlQueryBuilder.emptyInstance(query.repository().name(), model, BuilderType.DELETION);
        response.append(builder.push(connection).close());
        return response.close();
    }

    @Override
    public void delete(DataQuery<Object> query, Object value) {
        //todo
    }

    @Override
    public void update(DataQuery<Object> query, Object value) {
        //todo
    }

    @Override
    public void updateIf(DataQuery<Object> query, Object value, Predicate<Object> predicate) {
        //todo
    }

    @Override
    public void upsert(DataQuery<Object> query, Object value) {
        //todo
    }

    @Override
    public List<Object> findAll(DataQuery<Object> query) {
        //todo
        return null;
    }

    @Override
    public List<Object> findAll(DataQuery<Object> query, int limit) {
        //todo
        return null;
    }

    @Override
    public Object find(DataQuery<Object> query) {
        //todo
        return null;
    }

    @Override
    public boolean exists(DataQuery<Object> query) {
        // todo add filters
        return false;
    }

    @Override
    public long count(DataQuery<Object> query) {
        //todo
        return 0;
    }

    @Override
    public long sum(DataQuery<Object> query, String id) {
        //todo
        return 0;
    }

    @Override
    public double avg(DataQuery<Object> query, String id) {
        //todo
        return 0;
    }

    @Override
    public List<Object> order(DataQuery<Object> query, String id, int limit, SortedOrder order) {
        //todo
        return null;
    }

    @Override
    public <E> List<E> collect(DataQuery<Object> query, String id, Class<E> clazz) {
        //todo
        return null;
    }

    @Override
    public <E> List<E> collect(DataQuery<Object> query, String id, int limit, Class<E> clazz) {
        //todo
        return null;
    }

    @Override
    public <E> E collectSingle(DataQuery<Object> query, String id, Class<E> clazz) {
        //todo
        return null;
    }

    @Override
    public Object max(DataQuery<Object> query, String id) {
        //todo
        return null;
    }

    @Override
    public Object min(DataQuery<Object> query, String id) {
        //todo
        return null;
    }

    @Override
    public LayerFilterHandler<?, ?> filterHandler() {
        //todo
        return null;
    }
}
