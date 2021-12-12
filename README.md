# It's always Traverse?

Solving day 1 parts 1 and 2 with Typeclasses's you may have lying
around the home.  

https://adventofcode.com/2021/day/1

Namely Monoid, Traverse, Foldable and Applicative, with a bit of
zipping and Functors.

## How it works

See my youtube channel [Functional Justin](https://youtu.be/ziWwsXD9w7M) for a video
on this topic.

In a nutshell the idea is to take a list of elements, such as

`1,2,3`

and count the number of times a number is larger than the one that
proceeds it.

To solve this using the Traverse typeclass, I take advantage of the
fact that Traverse, used with the Const data type, gives us a way to
fold over the list. That fold is done with whichever datatype and
Monoid instance we provide. So providing a type that is smart enough
to count increasing following values when appended together, we are
able to provide a solution. 

See the source for details.

## References

My blog post and videos "What's Ap" cover the Applicative/Traverse in detail and are based on the papers below.

Scala Love conference talk https://youtu.be/_LDk9BU_Rmc
What's Ap - https://justinhj.github.io/2020/04/04/whats-ap.html

Applicative Programming with Effects McBride, Patterson 2008
The Essence of the Iterator Pattern Gibbons, Bruno
Do We Need Dependent Types? Fridlender
Read a Paper: Applicative Programming with Effects
