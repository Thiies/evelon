package dev.httpmarco.evelon.demo.h2;

import dev.httpmarco.evelon.common.query.response.ResponseType;
import dev.httpmarco.evelon.common.repository.Repository;
import dev.httpmarco.evelon.common.repository.RepositoryBuilder;
import dev.httpmarco.evelon.demo.HierarchyElement;
import dev.httpmarco.evelon.demo.HierarchyTestRepository;
import dev.httpmarco.evelon.demo.SimpleTestRepository;
import dev.httpmarco.evelon.h2.layer.H2SqlLayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class H2SimpleRepositoryTest {

    @Nested
    @DisplayName("H2 - Simple Repository Test")
    class SimpleTest {
        private final Repository<SimpleTestRepository> repository = RepositoryBuilder.of(SimpleTestRepository.class)
                .addAfter(H2SqlLayer.class)
                .build();

        @Test
        void creation() {
            assertEquals(ResponseType.SUCCESS, repository.query().create(new SimpleTestRepository(1, 200)).response());
        }
    }

    @Nested
    @DisplayName("H2 - Hierarchy Repository Test")
    class HierarchyTest {
        private final Repository<HierarchyTestRepository> repository = RepositoryBuilder.of(HierarchyTestRepository.class)
                .addAfter(H2SqlLayer.class)
                .build();

        @Test
        void creation() {
            assertEquals(ResponseType.SUCCESS, repository.query().create(
                    new HierarchyTestRepository("Alex", 200,
                            new HierarchyElement("xyz-1", 220)
                    )
            ).response());
        }
    }
}