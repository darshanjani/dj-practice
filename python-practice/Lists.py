__author__ = 'Darshan'

from math import pi

print([str(round(pi, i)) for i in range(1, 6)])

list = [1,2,3]
list2 = [x for x in range(3,5)]
list.extend(list2)
list.insert(3,7)
print(list)
print(list.count(3))
list.remove(3)  #This is not an index, but first element it finds
print(list)
list.reverse()
print(list)
del list[1] #This deletes by position, starting with zero
print(list)