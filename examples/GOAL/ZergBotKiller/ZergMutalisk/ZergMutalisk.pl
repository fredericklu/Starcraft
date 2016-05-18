:- dynamic
move/2,
position/2,
attack/2,
attack/3,
unitAmount/2,
base/4,
enemy/7,
self/2,
friendly/6,
chokepoint/2,
attack/1,
chokePoint/2,
groupLocation/2,
attack2/2,
enemyBase/2,
ready/1,
attack/0,
hitPoints/1,
enemyRace/1,
attacking/2,
leader/1,
leader/0
.

%enemyBase(X, Y) :- base(X, Y, 'true', _), friendly(Type,_ ,_,_, X1, Y1), 
%member(Type,["Zerg Hatchery", "Zerg Lair", "Zerg Hive"]) , distance(X, Y, X1, Y1, Res), Res > 10.

rallyLocation(X,Y, RX,RY) 
	:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
		sort(L, [[_,RX,RY]|_]). 
		
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	 