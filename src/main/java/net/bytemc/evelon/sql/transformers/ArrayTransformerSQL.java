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

package net.bytemc.evelon.sql.transformers;

import net.bytemc.evelon.sql.SQLElementStageTransformer;
import net.bytemc.evelon.sql.Stage;
import net.bytemc.evelon.sql.StageHandler;
import net.bytemc.evelon.sql.substages.CollectionObjectStage;
import java.util.Arrays;

public final class ArrayTransformerSQL implements SQLElementStageTransformer<Object[]> {

    @Override
    public Stage<?> transformTo() {
        return  StageHandler.getInstance().getStage(CollectionObjectStage.class);
    }

    @Override
    public Object transform(Object[] value) {
        return Arrays.asList(value);
    }

    @Override
    public Object[] rollback(Object value) {

        //TODO check this implementation
        throw new UnsupportedOperationException("rollback not supported for arrays");
    }

    @Override
    public boolean isElement(Class<?> type) {
        return type.isArray();
    }
}
