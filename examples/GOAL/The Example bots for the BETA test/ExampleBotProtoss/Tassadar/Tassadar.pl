:- dynamic
done/2,
probe/2,
ready/1,
nexusPosition/2.

allReady :- aggregate_all(count, ready(_), Count), Count > 3.