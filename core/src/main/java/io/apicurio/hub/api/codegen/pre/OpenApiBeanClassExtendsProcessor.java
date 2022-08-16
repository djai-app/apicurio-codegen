/*
 * Copyright 2021 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.hub.api.codegen.pre;

import io.apicurio.datamodels.combined.visitors.CombinedVisitorAdapter;
import io.apicurio.datamodels.core.models.Extension;
import io.apicurio.datamodels.core.models.common.IDefinition;
import io.apicurio.datamodels.openapi.models.OasSchema;
import io.apicurio.hub.api.codegen.CodegenExtensions;

/**
 * Pre processes the OpenAPI document to handle the x-codegen-extendsClass extension.
 * @author eric.wittmann@gmail.com
 */
public class OpenApiBeanClassExtendsProcessor extends CombinedVisitorAdapter {

    /**
     * @see io.apicurio.datamodels.combined.visitors.CombinedVisitorAdapter#visitSchemaDefinition(io.apicurio.datamodels.core.models.common.IDefinition)
     */
    @Override
    public void visitSchemaDefinition(IDefinition node) {
        OasSchema schema = (OasSchema) node;
        Extension extension = schema.getExtension(CodegenExtensions.EXTENDS_CLASS);
        if (extension != null && extension.value != null) {
            String baseClass = extension.value.toString();
            schema.removeExtension(CodegenExtensions.EXTENDS_CLASS);
            schema.addExtraProperty("extendsJavaClass", baseClass);
        }
    }

}