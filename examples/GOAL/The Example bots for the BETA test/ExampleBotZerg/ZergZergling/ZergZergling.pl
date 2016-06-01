:- dynamic
position/2,
attack/2,
unitAmount/2,
base/4,
enemy/4,
self/2,
friendly/6,
chokepoint/2,
attack/1,
attacking/0,
chokePoint/2,
groupLocation/2,
enemyBase/2,
ready/1,
attack/0,
hitPoints/1,
attacking/2,
moving/0,
condition/1,
hatcheryPosition/2.

% default distance check
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	 