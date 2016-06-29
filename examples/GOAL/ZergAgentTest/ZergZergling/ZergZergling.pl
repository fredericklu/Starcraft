:- dynamic
position/2,
unitAmount/2,
base/4,
self/2,
friendly/4,
chokepoint/2,
condition/1
.

% default distance check
distance(X1,Y1,X2,Y2,D) :- D is ((X2-X1)**2 + (Y2-Y1)**2).	 