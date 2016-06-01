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
done/2,
ready/1.

allReady :- aggregate_all(count, ready(_), Count), Count > 5.