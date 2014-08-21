import string
import random
from random import randint
#str1 = ''.join(random.choice(string.ascii_lowercase) for i in range(randint(1,250)))
#str2 = ''.join(random.choice(string.ascii_lowercase) for i in range(randint(1,len(str1))))
#print str1
#print str2
PEW = randint(1,10000)
wordlist = []
f = open('wordlist.txt','r')
wordlist = [line.strip() for line in open('wordlist.txt','r')]
str1 = ''.join(random.choice(wordlist) for i in range(randint(1,PEW)))
str2 = ''.join(random.choice(wordlist) for i in range(randint(1,PEW)))[:randint(1,len(str1))]
print str1
print str2
