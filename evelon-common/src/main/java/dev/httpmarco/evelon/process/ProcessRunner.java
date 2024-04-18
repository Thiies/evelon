package dev.httpmarco.evelon.process;

import dev.httpmarco.evelon.layer.Layer;
import dev.httpmarco.evelon.repository.Repository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
public abstract class ProcessRunner<Q> {

    @SuppressWarnings("unchecked")
    public void apply(@NotNull Process<Q, ?> process, @NotNull Repository<?> repository) {
        Q query = null;

        if (process instanceof AbstractEntryProcess<?, ?> entryProcess) {
            query = (Q) entryProcess.run(repository.entry());
        }

        if (query != null) {
            // todo add query method
            this.update(query);
        }
    }

    public abstract void update(Q query);

}