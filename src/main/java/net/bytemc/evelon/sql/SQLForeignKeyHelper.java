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

package net.bytemc.evelon.sql;

import net.bytemc.evelon.exception.StageNotFoundException;
import net.bytemc.evelon.repository.RepositoryClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SQLForeignKeyHelper {

    public static void convertToDatabaseElementsWithType(List<String> elements, ForeignKey... keys) {
        for (var key : keys) {
            var keyStage = StageHandler.getInstance().getElementStage(key.foreignKey().getType());
            if (keyStage == null) {
                throw new StageNotFoundException(key.foreignKey().getType());
            }
            if (keyStage instanceof SQLElementStage<?> elementStage) {
                elements.add(SQLHelper.getRowName(key.foreignKey()) + " " + elementStage.anonymousElementRowData(key.foreignKey(), new RepositoryClass<>(key.foreignKey().getType())) + " NOT NULL");
            }
        }
    }

    public static void convertToDatabaseForeignLink(List<String> elements, ForeignKey... keys) {
        for (var key : keys) {
            elements.add("FOREIGN KEY (" + key.parentField() + ") REFERENCES " + key.parentTable() + "(" + key.parentField() + ") ON DELETE CASCADE");
        }
    }

    public static Map<String, String> convertKeyObjectsToElements(ForeignKeyObject... keys) {
        var elements = new HashMap<String, String>();
        for (var key : keys) {
            elements.put(key.id(), key.value());
        }
        return elements;
    }

}