 :- dynamic
condition/1,
minerals/1,
gas/1,
builder/0,
gathering/1,
unit/2,
mineralField/4,
position/2,
constructionSite/2,
workerActivity/2,
self/2,
chokepoint/2,
base/4,
constructing/0,
friendly/6,
vespeneGeyser/3,
buildPool/2,
buildExtractor/2,
buildEvoChamber/2,
expandTo/2,
goGather/1,
buildColony/2,
build/3,
isMorphing/2,
move/2,
unitAmount/2,
buildHatchery/2,
spireBuild/2,
scout/0,
enemyBase/2,
attacking/2,
enemyRace/1.

		busy :- condition(List), member('gathering', List).
		
		cost("Zerg Spawning Pool", 200, 0).
		cost("Zerg Evolution Chamber", 75, 0).
		cost("Zerg Extractor", 75, 0).
		cost("Zerg Hatchery", 300, 0).
		cost("Zerg Hydralisk Den", 100, 50).
		cost("Zerg Creep Colony", 75, 0).
		cost("Zerg Spire", 200, 150).
						
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
				
	
		shouldMineVespene :- unitAmount("Zerg Extractor",RefCount),
				NeededWorkers is RefCount * 2,
				aggregate_all(count, workerActivity(_,gatheringGas), Count),
				Count < NeededWorkers.
								
		nearestBase(X, Y, RX, RY) 
		:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
			sort(L, [[_,RX,RY]|_]). 	