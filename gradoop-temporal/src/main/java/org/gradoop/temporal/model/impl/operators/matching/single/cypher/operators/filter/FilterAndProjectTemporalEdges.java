/*
 * Copyright © 2014 - 2021 Leipzig University (Database Research Group)
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
package org.gradoop.temporal.model.impl.operators.matching.single.cypher.operators.filter;

import org.apache.flink.api.java.DataSet;
import org.gradoop.flink.model.impl.operators.matching.common.query.predicates.CNF;
import org.gradoop.flink.model.impl.operators.matching.single.cypher.operators.PhysicalOperator;
import org.gradoop.flink.model.impl.operators.matching.single.cypher.pojos.Embedding;
import org.gradoop.temporal.model.impl.operators.matching.single.cypher.operators.filter.functions.FilterAndProjectTemporalEdge;
import org.gradoop.temporal.model.impl.pojo.TemporalEdge;

import java.util.List;

/**
 * Filters a set of {@link TemporalEdge} objects based on a specified predicate. Additionally, the
 * operator projects all property and temporal values to the output {@link Embedding} that are specified in the
 * given {@code projectionPropertyKeys}.
 * <p>
 * {@code Edge -> Embedding(
 *  [IdEntry(SourceId),IdEntry(EdgeId),IdEntry(TargetId)],
 *  [PropertyEntry(v1),PropertyEntry(v2)]
 * )}
 * <p>
 * Example:
 * <br>
 * Given an Edge {@code (0, 1, 2, "friendOf", {since:2017, weight:23})}, a predicate {@code "weight = 23"} and
 * list of projection property keys [since,isValid] the operator creates an {@link Embedding}:
 * <br>
 * {@code ([IdEntry(1),IdEntry(0),IdEntry(2)],[PropertyEntry(2017),PropertyEntry(NULL)])}
 *
 */
public class FilterAndProjectTemporalEdges implements PhysicalOperator {
  /**
   * Input graph elements
   */
  private final DataSet<TemporalEdge> input;
  /**
   * Predicates in conjunctive normal form
   */
  private final CNF predicates;
  /**
   * Property keys used for projection
   */
  private final List<String> projectionPropertyKeys;
  /**
   * Signals that the edge is a loop
   */
  private boolean isLoop;

  /**
   * Operator name used for Flink operator description
   */
  private String name;

  /**
   * New edge filter operator
   *
   * @param input Candidate edges
   * @param predicates Predicates used to filter edges
   * @param projectionPropertyKeys Property keys used for projection
   * @param isLoop is the edge a loop
   */
  public FilterAndProjectTemporalEdges(DataSet<TemporalEdge> input, CNF predicates,
                               List<String> projectionPropertyKeys, boolean isLoop) {
    this.input = input;
    this.predicates = predicates;
    this.projectionPropertyKeys = projectionPropertyKeys;
    this.isLoop = isLoop;
    this.setName("FilterAndProjectEdges");
  }

  @Override
  public DataSet<Embedding> evaluate() {
    return input
      .flatMap(new FilterAndProjectTemporalEdge(predicates, projectionPropertyKeys, isLoop))
      .name(getName());
  }

  @Override
  public void setName(String newName) {
    this.name = newName;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
