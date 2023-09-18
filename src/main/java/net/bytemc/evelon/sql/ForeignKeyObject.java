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

import net.bytemc.evelon.misc.Reflections;

import java.lang.reflect.Field;

public record ForeignKeyObject(String id, String value) {

    public static ForeignKeyObject of(Field primaryField, Object parent) {
        return new ForeignKeyObject(SQLHelper.getRowName(primaryField), "'" + Reflections.readField(parent, primaryField).toString() + "'");
    }
}
