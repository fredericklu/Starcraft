:- dynamic
ready/1,
done/2,
done/0,
drone/2,
enemy/1,
scout/2,
muta/2,
leader/1.

allReadyNotProtoss :- aggregate_all(count, ready(_), Count), Count > 5, enemy(Race), member(Race, ['Zerg', 'Terran', 'Unknown']).

allReadyProtoss :- aggregate_all(count, ready(_), Count), Count > 10, enemy('Protoss').