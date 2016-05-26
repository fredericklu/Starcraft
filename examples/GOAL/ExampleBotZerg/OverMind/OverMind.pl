:- dynamic
done/2,
drone/2,
ready/1,
hatcheryPosition/2.

allReady :- aggregate_all(count, ready(_), Count), Count > 5.