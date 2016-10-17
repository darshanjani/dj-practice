with open('Review.py') as f:
    line = f.readline()
    while line:
        startOfChar = -1
        countOnLine = 0
        colonFound = 0
        startCharFound = False
        for c in line:
            # print(c, end='')
            if c.isalpha() or c == '_':
                if not startCharFound:
                    startOfChar = countOnLine
                    # print('Found start char at', startOfChar, 'on line: ', line, end='')
                    startCharFound = True
            if c == ':':
                colonFound = countOnLine
            countOnLine += 1
        if colonFound != 0:
            # print(line, end='')
            # print("Start of first char:", startOfChar, "Colon at:", colonFound)
            newLine = line[:startOfChar] + "'" + line[startOfChar:colonFound] + "'" + line[colonFound:]
            print(newLine, end='')
        else:
            print(line, end='')
        line = f.readline()

print('string'[:0])