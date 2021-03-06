In this project, I put a new scheduler into xv6. It is a mix of two schedulers: the multi-level feedback queue (MLFQ) and the lottery scheduler.

The basic idea is simple: Build a simple two-level scheduler which first places jobs into the high-priority queue. When a job uses its time slice on the first queue, move it to the lower-priority queue; jobs on the lower-priority queue should run for two time slices before relinquishing the CPU.

When there is more than one job on a queue, each should run in proportion to the number of tickets it has; the more tickets a process has, the more it runs. Each time slice, a randomized lottery determines the winner of the lottery; that winning process is the one that runs for that time slice.