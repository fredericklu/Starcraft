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
refinery/3,
workerActivity/2,
id/1,
me/1,
chokepoint/2,
base/4,
constructing/0,
friendly/5,
build/3,
gather/1,
atEnemy/0,
enemybase/2,
ownbase/2,
cmd/2,
pos/2,
attacking/2,
unit/3,
condition/1,
unitAmount/2.

		busy :- condition(L),(member('constructing',L) ; member('gathering',L)).
		cost("Protoss Pylon", 100, 0).
		cost("Protoss Gateway", 150, 0).
								
		buildingLocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (constructionSite(BX,BY,'true'), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 
						
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
		
		chokepointlocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 	
				
		nearestBase(X, Y, RX, RY) 
				:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 	
				
		shouldMineVespene :- unit('Terran Refinery',RefCount),
								NeededWorkers is RefCount * 2,
								aggregate_all(count, workerActivity(_,gatheringGas), Count),
								Count < NeededWorkers.
								
		buildLocation(X, Y) :- 
				ownbase(TX, TY),
				buildingLocation(TX, TY, X, Y).
				
		buildPylonLocation(X, Y) :- 
				ownbase(TX, TY),
				buildingPylonLocation(TX, TY, X, Y).
						
		buildingPylonLocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (constructionSite(BX,BY,_), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 
		
		commandCenter(X,Y)
			:- friendly(_, "Terran Command Center", _ , X, Y).
			
			
		findNewPosition(X,Y)
			:- attacking(Enemy,Self), id(Self), ownbase(X,Y).
			
%			unit(Enemy,X1,Y1), pos(X2,Y2), D is X2-X1, G is Y2-Y1,
%			((D > 0, G > 0, X is X1+random(4), Y is Y1+random(4)) ; (D<0,G<0, X is X1-random(4), Y is Y1-random(4))
%			; (D > 0,G<0, X is X1+random(4), Y is Y1-random(4)) ; (D<0,G>0, X is X1-random(4), Y is Y1+random(4))).	