# ConsistAnts

[![Build Status](https://travis-ci.org/ConsistAnts/ConsistAnts.svg?branch=master)](https://travis-ci.org/ConsistAnts/ConsistAnts)

In large-scale software systems, quantitative software quality models can be used to model the system behaviour and support the satisfaction of increasing quality requirements on an abstract level. Such models, however, are only of use if they actually represent the system behaviour in a complete and accurate fashion. To verify whether the consistency between model and system behaviour is high enough, checking tools are required.

In the ConsistAnts project we have implemented tools that provide support to perform consistency checks of software event logs with given quantitative software quality models. For that we introduce a consistency checking algorithm that is based on footprint matrices as known from process theory. 

With our tools we support the use of Probabilistic Automata Models as well as Stochastic Regular Expressions. 

We have also implemented two different learning algorithms that allow to learn quantitative models from an observed event log. Such learned models can be used as the foundation for further user modifications or be used as a reference point for later checks.

The project is implemented in means of an Eclipse plugin but all functionality is also exposed via a command line interface ([Usage](https://github.com/ConsistAnts/ConsistAnts/wiki/Using-the-Consistency-Checking-Command-Line-Interface)).



## Getting Started

To set up a development environment please follow our step-by-step guide [in the wiki](https://github.com/ConsistAnts/ConsistAnts/wiki/Developer-Setup)


## Help

If you need help with the ConsistAnts project you can find a few useful articles on our [wiki](https://github.com/ConsistAnts/ConsistAnts/wiki). 

Apart from that there is also a [Gitter chatroom](https://gitter.im/consistants) for further questions.
