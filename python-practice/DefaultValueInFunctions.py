__author__ = 'Darshan'


def ask_ok(prompt, retries=4, reminder='Please try again!'):
    while True:
        ok = input(prompt)
        if ok in ('y', 'ye', 'yes'):
            return True
        if ok in ('n', 'no', 'nop', 'nope'):
            return False
        retries = retries - 1
        if retries < 0:
            raise ValueError('invalid user response')
        print(reminder)


ask_ok('Do you really want to quit? ')
ask_ok('Try for maximum 2 times? ', 2)
ask_ok('Ok to overwrite file? ', 2, 'Only yes or no allowed')


def f(a, L=[]):
    L.append(a)
    return L


print(f(1))
print(f(2))
print(f(3))


def f2(a, L=None):
    if L is None:
        L = []
    L.append(a)
    return L


print(f2(1))
print(f2(2))
print(f2(3))
