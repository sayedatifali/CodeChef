def dfs(array, visited, i):

    count = 0

    while not visited[i]:
        visited[i] = True
        i = array[i]
        count += 1

    return count

def solve(array, N):

    visited = [False]*N
    numbers = []

    for i in xrange(0, N):
        if not visited[i]:
            num = dfs(array, visited, i)
            numbers.append(num)

#    print numbers

    if len(numbers) == 1:
        ans = numbers[0]
    else:
        ans = lcmm(numbers)

    ans %= 1000000007

    return ans

def gcd(a, b):
    while b:
        a, b = b, a % b
    return a

def lcm(a, b):
    return (a * b) // gcd(a, b)

def lcmm(args):
    return reduce(lcm, args)

if __name__=="__main__":

    T = int(raw_input())

    while T:
        N = int(raw_input())
        line = str(raw_input())
        line = line.split()
        array = []
        for num in line:
            array.append(int(num) - 1)

        ans = solve(array, N)
        print ans

        T -= 1

