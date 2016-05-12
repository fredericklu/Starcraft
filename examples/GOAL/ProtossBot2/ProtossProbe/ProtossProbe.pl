 :- dynamic
builder/0,
minerals/1,
gathering/1,
unit/2,
gas/1,
mineralField/4,
supply/2,
position/2,
constructionSite/3,
workerActivity/2,
id/1,
chokepoint/2,
base/4,
constructing/0,
friendly/5,
vespeneGeyser/3,
gather/1,
build/3,
beginPylon/2.


	busy :- constructing ; gathering(_).
	cost("Protoss Pylon", 100, 0).
	cost("Protoss Nexus", 400, 0).
	cost("Protoss Gateway", 150, 0).
	cost("Protoss Assimilator", 100, 0).
	cost("Protoss Forge", 150, 0).
	cost("Protoss Cybernetics Core", 200, 0).
	cost("Protoss Photon Cannon", 150, 0).
						
	distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
		
	buildingLocation(X,Y, RX,RY) 
		:-	findall([D,BX,BY], (constructionSite(BX,BY, false), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]).
		
	chokepointlocation(X,Y, RX,RY) 
		:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]).
				
	
	shouldMineVespene :- unit("Protoss Assimilator",RefCount),
			NeededWorkers is RefCount * 2,
			aggregate_all(count, workerActivity(_,gatheringGas), Count),
			Count < NeededWorkers.
								
	nearestBase(X, Y, RX, RY) 
		:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]).
	