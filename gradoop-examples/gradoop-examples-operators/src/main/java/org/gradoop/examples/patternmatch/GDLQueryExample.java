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
package org.gradoop.examples.patternmatch;

import java.io.File;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.gradoop.common.model.api.entities.GraphHead;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.examples.common.SocialNetworkGraph;
import org.gradoop.flink.io.impl.dot.DOTDataSink;
import org.gradoop.flink.model.impl.epgm.GraphCollection;
import org.gradoop.flink.model.impl.epgm.LogicalGraph;
import org.gradoop.flink.util.FlinkAsciiGraphLoader;
import org.gradoop.flink.util.GradoopFlinkConfig;

/**
 * A self-contained example on how to use the query engine in Gradoop.
 *
 * The example uses the graph in dev-support/social-network.pdf
 */
public class GDLQueryExample {

  /**
   * Runs the program on the example data graph.
   *
   * The example provides an overview over the usage of the {@link LogicalGraph#query(String)}
   * method. It showcases who a user defined (GDL) query can be applied to a graph.
   * Documentation and usage examples can be found in the projects wiki.
   *
   * Using the social network graph {@link SocialNetworkGraph}, the program will:
   * <ol>
   *   <li>create the graph based on the given gdl string</li>
   *   <li>run the query method with a user defined (GDL) query string</li>
   *   <li>print all found matches</li>
   * </ol>
   *
   * @param args arguments
   * @see <a href="https://github.com/dbs-leipzig/gradoop/wiki/Unary-Logical-Graph-Operators">
   * Gradoop Wiki</a>
   * @throws Exception in case sth goes wrong
   */
  public static void main(String[] args) throws Exception {
    // create flink execution environment
    ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
    // create loader
    FlinkAsciiGraphLoader loader = new FlinkAsciiGraphLoader(GradoopFlinkConfig.createConfig(env));
    // load data
    loader.initDatabaseFromString(SocialNetworkGraph.getGraphGDLString());
    // load graph
    LogicalGraph socialNetwork = loader.getLogicalGraph();
    // run the query method (result will be a collection of matched subgraphs)
    GraphCollection matches = socialNetwork.query(
      "MATCH (u1:Person)<-[:hasModerator]-(f:Forum)" +
      "(u2:Person)<-[:hasMember]-(f)" +
      "WHERE u1.name = \"Alice\"");
    // print the matches
    matches.print();

    // Retrieve an existing GradoopId from the first graph in the collection
    //GradoopId validGradoopId = null;
    //System.out.println("t");
    //int count = 1;
    //for (GraphHead graphHead : matches.getGraphHeads().collect()) {
    //  validGradoopId = graphHead.getId();

      // print the graph to the console for verification
//      matches.getGraph(validGradoopId).writeTo(new DOTDataSink("gradoop_exports" + File.separator + "GDLQuery" + count +".dot", true,
//        DOTDataSink.DotFormat.HTML));

  //    count++;
    //}


    // finally execute
   // env.execute("GDLQuery - Task 1");
    System.out.println("GDLQuery");
  }
}
