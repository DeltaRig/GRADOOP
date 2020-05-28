/*
 * Copyright © 2014 - 2020 Leipzig University (Database Research Group)
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
package org.gradoop.flink.model.impl.operators.statistics.writer;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.gradoop.flink.model.impl.operators.matching.common.statistics.GraphStatisticsReader;

/**
 * Class to write the statistic of a graph in a file.
 */
public class StatisticWriter {

  /**
   * Write the statistic for a given logical graph in a CSV file.
   * @param tuples tuples the tuples to write (one row per tuple, tuple fields separated by
   * GraphStatisticsReader.TOKEN_SEPARATOR).
   * @param filePath the path for the CSV file
   * @param <T> the tuple containing statistics
   */
  public static <T extends Tuple> void writeCSV(final DataSet<T> tuples, final String filePath) {
    writeCSV(tuples, filePath, false);
  }

  /**
   * Writes tuples as CSV file in a filepath.
   * @param tuples the tuples to write (one row per tuple, tuple fields separated by
   * GraphStatisticsReader.TOKEN_SEPARATOR).
   * @param filePath the path to write the tuples in.
   * @param overWrite should the target file be overwritten if it already exists?
   * @param <T> the tuple containing statistics
   */
  public static <T extends Tuple> void writeCSV(
      final DataSet<T> tuples, final String filePath, final boolean overWrite) {
    tuples.writeAsCsv(
        filePath,
        System.lineSeparator(),
        GraphStatisticsReader.TOKEN_SEPARATOR,
        overWrite ? WriteMode.OVERWRITE : WriteMode.NO_OVERWRITE)
       .setParallelism(1);
  }
}