:- dynamic
self/2,
queueSize/1,
friendly/4,
isMorphing/2,
minerals/1,
unitAmount/2,
gas/1,
supply/2,
train/1,
probe/2,
done/0,
enemyBase/2,
rallyPoint/2,
chokepoint/2,
base/4,
commandCenterPosition/2,
unitCondition/2.

% default distance check
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	