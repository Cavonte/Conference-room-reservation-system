# Skynet-Project

## Description Of The Project

Your task is to develop an on-line conference room reservation system that is used by a
college facility similar (but not identical) to the one used by ENCS for capstone projects
(https://capstone.encs.concordia.ca/view_r.php?id=4). Do not try to replicate the
ENCS system.
The system maintains a directory of rooms. Each room may be reserved at a number of
time slots. The users of the system are already registered with the facility. Multiple users
can access the system in order to reserve the facilities. Alternatively, users can access the
system in order to cancel an existing reservation, modify an existing reservation, or merely
to view reservations. The system maintains a registry of all reservations. Before performing
any task, users must be identifed and authenticated by the system.
The system allows multiple users to access its registry in order to view its status (all avail-
abilities and reservations), but only one user may access a room at any given time in order
to create, modify or cancel a reservation. A user who wants to reserve a time slot for a room
that is already booked at that time can be placed on a waiting list and be able to obtain the
room upon cancellation of the current reservation. Upon obtaining such reservation, the user
is removed from any and all other waiting lists on any other room that has been reserved
over the same time slot.

For any given room, the operations to create, to modify and to cancel a reservation are all
"write" operations, whereas view is a "read" operation. Writers operate in self-exclusion,
i.e. only one writer can be active at a time. If a client wishes to write and the resource is
not available (another client is writing on the resource), then the client must wait, until the
resource becomes available. Writers and readers operate in mutual exclusion. The system
may allow multiple readers at a time. The system must provide *safety*, *liveness* and *fairness*.

You must include some restrictions in your software: A room can be booked on a repeated
time-slot, but only for some time period. A user may create only up to some maximum
allowable number of reservations per week.

Your software system will be composed of a **Software Requirements Specification**, a **Software
Architecture Document** and of course of the code repository. Your system must be object-oriented. 
Feel free to implement your system in any language.
