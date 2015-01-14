from random import randrange

if __name__=="__main__":
	N = 800000
	N = 80000

	print N

	for i in xrange(0, N):
		num = randrange(10000)
		print num,

	M = 200000
	M = 2000
	print '\n',M

	while M > 0:
		op = randrange(2)
		u = randrange(N)
		v = randrange(N)
		if u > v:
			u ^= v
			v ^= u
			u ^= v
		if op == 0:
			rot = randrange(60)
			print op,u,v,rot
			pass
		if op == 1:
			print op,u,v
			pass
		M -= 1
