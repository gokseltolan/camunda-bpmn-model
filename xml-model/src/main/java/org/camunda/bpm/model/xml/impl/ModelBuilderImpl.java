/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.model.xml.impl;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.type.ModelElementTypeBuilderImpl;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public class ModelBuilderImpl extends ModelBuilder {

  private final List<ModelElementTypeBuilderImpl> typeBuilders = new ArrayList<ModelElementTypeBuilderImpl>();
  private final ModelImpl model;

  public ModelBuilderImpl(String modelName) {
    model = new ModelImpl(modelName);
  }

  public ModelElementTypeBuilder defineType(Class<? extends ModelElementInstance> modelInstanceType, String typeName) {
    ModelElementTypeBuilderImpl typeBuilder = new ModelElementTypeBuilderImpl(modelInstanceType, typeName, model);
    typeBuilders.add(typeBuilder);
    return typeBuilder;
  }

  public Model build() {
    for (ModelElementTypeBuilderImpl typeBuilder : typeBuilders) {
      typeBuilder.buildTypeHierarchy(model);
    }
    for (ModelElementTypeBuilderImpl typeBuilder : typeBuilders) {
      typeBuilder.performModelBuild(model);
    }
    return model;
  }

}