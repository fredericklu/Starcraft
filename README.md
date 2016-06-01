## StarCraft: Brood War in Multi-Agent Systems

Travis CI: [![Build Status](https://travis-ci.org/eishub/Starcraft.svg?branch=master)](https://travis-ci.org/eishub/Starcraft)
Coverage: [![Coverage Status](https://coveralls.io/repos/github/eishub/Starcraft/badge.svg?branch=master)](https://coveralls.io/github/eishub/Starcraft?branch=master)

This project creates a bridge between [BWAPI](https://code.google.com/p/bwapi/ "BWAPI") for [StarCraft: Brood War](http://us.blizzard.com/en-us/games/sc/ "StarCraft: Brood War") and EIS-enabled Multi-Agent Systems. 

The environment interface standard ([EIS](https://github.com/eishub/eis/wiki "EIS")) has been developed to facilitate connecting software agents to environments. 

For tech trees see [TechTrees](http://www.teamliquid.net/forum/brood-war/226892-techtree-pictures "TechTrees").

### Requirements
* Java
* StarCraft: Brood War
* GOAL

### Quick Setup
1. Download the MSI Installer here: https://we.tl/0yBXI2plGE
2. Install the Starcraft Environment.
3. Follow the install guide in the MSI Installer.
4. The first time playing a map can take a while because of the map generation (Note that this can take up to 2 minutes).

### Current status

- Most units can be controlled by the MAS.
- The MAS is capable of playing all three races, see examples for GOAL code.

### Project overview
The project contains the following folders:

* **EISBW:** The EIS implementation of BWAPI. 
* **examples:** A collection of examples for different multi-agent systems.
* **libs:** Jar-files required for building the interface and running the various examples.
