:- dynamic
minerals/1,
gas/1,
enemyBase/2,
position/2,
attack/2,
unitAmount/2,
base/4,
enemy/7,
self/2,
friendly/6,
chokepoint/2,
morph/1,
attack/1,
hitPoints/1.

rallyLocation(X,Y, RX,RY) 
	:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
		sort(L, [[_,RX,RY]|_]). 
		
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	  