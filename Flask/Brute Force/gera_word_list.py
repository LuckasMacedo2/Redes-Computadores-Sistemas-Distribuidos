import itertools

from more_itertools import *

a = list(itertools.product('0123ABCD', repeat=8))

print(len(a))

c = open('word_list.txt', 'w')

for l in a:
    s = ""
    s += l[0]
    s += l[1]
    s += l[2]
    s += l[3]
    s += l[4]
    s += l[5]
    s += l[6]
    s += l[7]
    s += "\n"
    c.write(s)

c.close()