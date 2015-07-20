%buildings
cost('Terran Supply Depot',100,0,0).
cost('Terran Barracks',150,0,0).
cost('Terran Engineering Bay',125,0,0).
cost('Terran Academy',150,0,0).
cost('Terran Command Center',400,0,0).
cost('Terran Refinery',100,0,0).
cost('Terran Bunker',100,0,0).
cost('Terran Factory',200,100,0).
cost('Terran Missile Turret',75,0,0).
cost('Terran Starport',150,100,0).
cost('Terran Armory',100,50,0).
cost('Terran_Infantry_Weapons', 100,100,0).
cost('Terran Infantry Armor',100,100,0).
cost('U-238 Shells',100,150,0).
cost('Caduceus Reactor',150,150,0).
cost('Terran Firebat',50,25,2).
cost('Terran Medic',50,25,2).	
cost('Terran Marine',50,0,2).
cost('Terran SCV',50,0,2). 

buildTilePosition(X,Y) :- percept(buildTilePosition(X,Y)).
position(X,Y) :- percept(position(X,Y)).

walkDistanceTo(X1,Y1,Res):-position(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distanceTo(X1,Y1,Res):-buildTilePosition(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2).
