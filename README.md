## StarCraft: Brood War in Multi-Agent Systems

This project creates a bridge between [BWAPI](https://code.google.com/p/bwapi/ "BWAPI") for [StarCraft: Brood War](http://us.blizzard.com/en-us/games/sc/ "StarCraft: Brood War") and EIS-enabled Multi-Agent Systems. 

The environment interface standard ([EIS](https://github.com/eishub/eis/wiki "EIS")) has been developed to facilitate connecting software agents to environments. 

### Requirements
* Java
* StarCraft: Brood War
* GOAL

### Quick Setup
1. Install StarCraft: Brood War and update to version 1.16.1.
3. Download this project (Download ZIP in the right-side menu).
4. Move contents of libs/WINDOWS folder to your windows folder or another folder in your PATH
5. Move contents of Starcraft folder to your starcraft installation directory
6. Extract sscai.rar to the maps folder of starcraft
7. Move client-bridge-x86.dll and mapData to your eclipse installation directory
8. Start Eclipse as Administrator (Important)
9. Start ChaosLauncher as Administrator (Important)
10. Run one of the examples in the examples-folder using GOAL.
11. Run SCBW through ChaosLauncher with the BWAPI plugin enabled.
12. Start a new game using Terran as player on the map (2)Destination (in the sscai maps folder) and watch the agents play!

### Current status

- Most units can be controlled by the MAS.
- Only a subset of the Terran units have had implemented special abilities:
	- Marine (Stimpacks)

### Project overview
The project contains the following folders:

* **EISBW:** The EIS implementation of BWAPI. 
* **examples:** A collection of examples for different multi-agent systems.
* **libs:** Jar-files required for building the interface and running the various examples.
