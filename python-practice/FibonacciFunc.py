__author__ = 'Darshan'


def fib(n):
    """Print a fibonacci series up to n"""
    a, b = 0, 1
    while a < n:
        print(a, end=',')
        a, b = b, a + b
    print()


fib(1000)
f = fib
f(100)


def fib2(n):
    """Print a fibonacci series up to n"""
    a, b = 0, 1
    result = []
    while a < n:
        result.append(a)
        a, b = b, a + b
    return result


f500 = fib2(500)
print(f500)
