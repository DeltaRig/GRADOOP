# GRADOOP - Customized Testing and Modifications

This repository contains a forked version of [GRADOOP](https://github.com/dbs-leipzig/gradoop) with modifications and testing based on the research in [Distributed Temporal Graph Analytics with GRADOOP](https://link.springer.com/article/10.1007/s00778-021-00667-4). GRADOOP is a scalable platform for distributed graph analytics, supporting complex graph operations over temporal data.

## Overview of Modifications

This project explores adjustments to certain parameters within GRADOOP to analyze their effects on the results of various graph processing operations. The modifications were tested by running examples provided in the original repository, along with new configurations to examine potential performance changes.

### System Specifications

All tests were conducted on a personal machine with the following specifications:
- **Processor**: Intel Core i5
- **Operating System**: Linux

The tests included the default examples and operations outlined in the GRADOOP documentation. Adjustments were made to certain parameters to observe any variations in execution and output.

## Setup and Testing

1. **Cloning and Initial Setup**: 
   - The tutorial repository was first cloned and explored to gain a better understanding of GRADOOP’s functionality.

2. **Testing Workflow**:
   - Executed all unit tests provided by the original GRADOOP repository.
   - Ran at least one example for each major operator highlighted in the article, including those that handle temporal graph processing tasks.

3. **Parameter Modifications**:
   - Experimented with various code and parameter changes, observing how they impacted performance and results.

### Testing with Pantanal (PUCRS LAD)

An attempt was made to run the code on Pantanal, the high-performance computing cluster at PUCRS LAD. However, it was challenging to determine the correct configuration needed to run parallel processes such as Linear, CBA90, and CBA40, as implemented by the original GRADOOP researchers. Only basic tests could be run on Pantanal using the command:

```shell
mvn clean install
```

The lack of clear documentation on parallel execution configurations for Pantanal prevented full-scale testing in this environment.

## Future Work
Further exploration is needed to understand how to configure GRADOOP for optimized parallel execution in cluster environments like Pantanal. Collaborating with GRADOOP’s community or consulting additional documentation might help in achieving efficient distributed processing for temporal graph analytics on a larger scale.