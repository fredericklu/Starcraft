:- dynamic
position/2,
unitAmount/2,
base/4,
enemy/7,
self/2,
ready/1,
friendly/6,
chokepoint/2,
enemyBase/2,
attack/2,
attack/0,
condition/1,
sieged/0,
unsieged/0.

% default distance check
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).