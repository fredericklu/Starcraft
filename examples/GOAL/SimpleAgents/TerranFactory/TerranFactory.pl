 rallyLocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 				
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	
		
		buildLocation(X, Y) :- 
				percept(friendly(_, "Terran Command Center", _,_ ,_ , _, _ , TX, TY)),
				buildingLocation(TX, TY, X, Y).
				
		buildingLocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (percept(constructionSite(BX,BY)), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 		