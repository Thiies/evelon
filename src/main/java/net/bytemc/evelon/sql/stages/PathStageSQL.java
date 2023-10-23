/*
 * Copyright 2019-2023 ByteMC team & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bytemc.evelon.sql.stages;

import net.bytemc.evelon.misc.Pair;
import net.bytemc.evelon.repository.RepositoryClass;
import net.bytemc.evelon.sql.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.nio.file.Path;

public final class PathStageSQL implements SQLElementStage<Path> {

    @Override
    public String elementRowData(@Nullable Field field, RepositoryClass<Path> repository) {
        return SQLType.TEXT.toString();
    }

    @Override
    public Pair<String, String> elementEntryData(RepositoryClass<?> repositoryClass, @Nullable Field field, Path object) {
        // change path symbol, because mariadb ignore this symbol
        return new Pair<>(SQLHelper.getRowName(field), Schema.encloseSchema(object.toString().replaceAll("\\\\", "/")));
    }

    @Override
    public Path createObject(RepositoryClass<Path> clazz, String id, SQLResultSet.Table table) {
        return Path.of(table.get(id, String.class));
    }

    @Override
    public boolean isElement(Class<?> type) {
        return type.equals(Path.class);
    }
}
