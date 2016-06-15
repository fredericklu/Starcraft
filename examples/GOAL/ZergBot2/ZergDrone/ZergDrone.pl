 :- dynamic
builder/0,
minerals/1,
gas/1,
gathering/1,
unitAmount/2,
mineralField/4,
position/2,
constructionSite/2,
workerActivity/2,
self/2,
chokepoint/2,
base/4,
constructing/0,
friendly/5,
vespeneGeyser/3,
buildPool/2,
buildExtractor/2,
buildEvoChamber/2,
expandTo/2,
goGather/1,
buildHydraDen/2,
buildColony/2,
stop/0,
build/3,
isMorphing/2,
move/2.

		busy :- constructing ; gathering(_).
		cost("Zerg Spawning Pool", 200, 0).
		cost("Zerg Evolution Chamber", 75, 0).
		cost("Zerg Extractor", 75, 0).
		cost("Zerg Hatchery", 300, 0).
		cost("Zerg Hydralisk Den", 100, 50).
		cost("Zerg Creep Colony", 75, 0).
		cost("Zerg Queens Nest", 150, 100).
						
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
		
		chokepointlocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]).
				
	
		shouldMineVespene :- unitAmount("Zerg Extractor",RefCount),
				NeededWorkers is RefCount * 2,
				aggregate_all(count, workerActivity(_,gatheringGas), Count),
				Count < NeededWorkers.
								
		nearestBase(X, Y, RX, RY) 
		:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]). 	