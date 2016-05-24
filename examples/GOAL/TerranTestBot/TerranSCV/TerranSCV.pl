 :- dynamic
condition/1,
minerals/1,
gas/1,
builder/0,
gathering/0,
mineralField/4,
constructionSite/2,
workerActivity/2,
self/2,
chokepoint/2,
base/4,
friendly/6,
unitAmount/2,
supply/2,
constructing/0,
backupBuilder/0,
vespeneGeyser/2.

		busy :- constructing ; gathering.
		
		cost("Terran Command Center",400,0).
		cost("Terran Supply Depot", 100, 0).
		cost("Terran Barracks", 150, 0).
		cost("Terran Bunker", 100, 0).
		cost("Terran Refinery", 100, 0).
		cost('Terran Academy',150,0).
		cost("Terran Engineering Bay", 125, 0).
		cost("Terran Factory", 200, 100).
		cost('Terran Starport',150,100).
		cost("Terran Science Facility", 100, 150).
						
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
			
				
		shouldMineVespene :- unitAmount('Terran Refinery',RefCount),
				     NeededWorkers is RefCount * 1,
				     aggregate_all(count, workerActivity(_,gatheringGas), Count),
				     Count < NeededWorkers.
			