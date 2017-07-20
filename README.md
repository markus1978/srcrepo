## About
Srcrepo (source-ree-po) is an Eclipse based framework for reverse engineering whole
source code repositories and the contained software projects. Srcrepo currently
supports Git repositories containing Eclipse Java projects (i.e. Java source code with Eclipse meta data as ".project" files).

## Getting started
**Warning**, research project ahead! Srcrepo is not (yet) intended to be a regular released, well maintained, or somehow ready to use product. There is no real build process, test coverage is bad, and the dependency management has some manual components. Expect some mild pain.

### Requirements
* Eclipse (mars and up, mars preferred)
* at least EMF, MoDisco, egit plugins (look at plugin manifest for details)
* Apache Ivy and Ivy for eclipse (IvyDE)
* You should be familiar with Git, EMF, and Ivy.

### First Steps (in memory only)
* Read our last paper to see what this is all about:  [download .pdf](http://www.markus-scheidgen.de/wp-content/uploads/2017/03/ScheidgenModelsward2017-cr.pdf).
* clone this repository (https://github.com/markus1978/srcrepo)
* also clone https://github.com/markus1978/emf-compress and https://github.com/markus1978/jstattrack (these are not released dependencies of ours)
* import all Eclipse plugins (each subdirectory "de.hub.srcrrepo.\*" contains a plugin) via *Import/general/existing eclipse projects...*. Also import all plugins from jstattrack and emf-compress.
* We use Apache ivy to manage dependencies. Get all ivy dependencies for all ivy files you can find.
* close projects "de.hub.srcrrepo.emffrag.\*" for now
* run "generate code" for all .genmodels found in "de.hub.\*/models/\*.genmodel"
* all projects should compile, resolve errors if necessary
* Have a look at "de.hub.srcrrepo.tests/\*\*/MoDiscoGitImportTest.java" to see how srcrepo can be used to clone Git repos, derive models from cloned repos, and how to add the reverse engineered Java code to the models.
* Everything you program or run (including the given tests) need to run in an Eclipse environment. Srcrepo uses Modisco which uses Eclipse JDT which requires a running Eclipse. You should run your JUnit tests as Eclipse plugin-tests.
* Use the created project models as regular EMF models and do your OCL, model transformation, etc. magic as you like.

### Second Steps (using a database)
* You need a running mongodb. Better use a fresh one; we don't want to mess up your existing data.
* You need emf-fragments, which is our EMF->mongodb persistence layer. Clone emf-fragments and switch to the "emffrag4.0" branch (https://github.com/markus1978/emf-fragments).
* Import all emf-fragments plugins to your workspace and run "generate code" for all .genmodels you can find.
* Re-open the "de.hub.srcrrepo.emffrag.\*" projects. Everything should compile; resolve errors when necessary.
* Look at "de.hub.srcrepo.emffrag.tests/src/\*\*/MongoDBImportMoDiscoModelTests" to lear how to clone and import repositories to Mongodb.
* Look at "de.hub.srcrepo.emffrag.tests/src/\*\*/MongoDBImportedMoDiscoModelTests" to learn how to access repository models previously imported to Mongodb.

### Third Step (going headless and wring 'commands')
* Learn how Eclipse products are build. Have a look at "de.hub.srcrepo.emffrag/srcrepo.product". Use this product to build a headless CLI application.
* Srcrepo contains a series of commands that can be invoked from the command line. They can be used to import Git repos to Mongodb, list imported repos in Mongodb, delete, get statistics, etc.
* Look at "de.hub.srcrepo.emffrag.commands" to see how commands are written. Add your own commands that do some magic with import repos.

## License
srcrepo and all related software projects are licenced under the Apache License 2.0. (see LICENSE-2.0.html)

Copyright 2013 Markus Scheidgen
