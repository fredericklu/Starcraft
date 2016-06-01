:- dynamic
done1/2,
done2/2,
scv/2,
ready/1,
commandCenterPosition/2,
mineVespene/0.

allReady :- aggregate_all(count, ready(_), Count), Count > 5.