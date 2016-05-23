 :- dynamic
condition/1,
minerals/1,
gas/1,
builder/0,
gathering/0,
mineralField/4,
constructionSite/3,
workerActivity/2,
self/2,
chokepoint/2,
base/4,
friendly/6,
unitAmount/2,
supply/2.

	% Indicates when agent is busy gathering
	busy :- gathering.
	
	% The cost of declared buildings (for every new building add the cost here)
	cost("Protoss Pylon", 100, 0).
	cost("Protoss Nexus", 400, 0).
	cost("Protoss Gateway", 150, 0).
						
	% default distance check				
	distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
				
	% example code for gas gathering handling
	shouldMineVespene :- unitAmount("Protoss Assimilator",RefCount),
			     NeededWorkers is RefCount * 2,
			     aggregate_all(count, workerActivity(_,gatheringGas), Count),
			     Count < NeededWorkers.
	
	% building location near base	
	buildingLocation(X,Y, RX,RY) 
		:-	findall([D,BX,BY], (constructionSite(BX,BY, _), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]).
						