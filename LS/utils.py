def inmultire(X, Y):
    if len(X[0]) != len(Y):
        return -1

    matrix = []

    for i in range(0, len(X)):
        row = []
        for j in range(0, len(Y[0])):
            sum = 0
            for k in range(0, len(Y)):
                sum += X[i][k] * Y[k][j]
            row.append(sum)
        matrix.append(row)
    return matrix

def transpsusa(X):
    rez = [[X[j][i] for j in range(len(X))] for i in range(len(X[0]))]
    return rez

def cofactor(a,i,j):
    b = []
    for linie in range((len(a))):
        if linie != i:
            l = []
            for coloana in range(len(a[0])):
                if coloana != j:
                    l.append(a[linie][coloana])
            b.append(l)
    return b

def determinant(A, total=0):
    #store indices in list for row referencing
    indices = list(range(len(A)))

    # Section 2: when at 2x2 submatrices recursive calls end
    if len(A) == 2 and len(A[0]) == 2:
        val = A[0][0] * A[1][1] - A[1][0] * A[0][1]
        return val


    for fc in indices: #for each focus column
        # find the submatrix ...
        As = A # copy matrix
        As = As[1:]  # remove the first row
        height = len(As)  # D)

        for i in range(height):
            # for each remaining row of submatrix ...
            # remove the focus column elements
            # delete current column
            As[i] = As[i][0:fc] + As[i][fc + 1:]

        sign = (-1) ** (fc % 2)  # F)
        # pass submatrix recursively
        sub_det = determinant(As)

        # total all returns from recursion
        total += sign * A[0][fc] * sub_det

    return total

def inversa(X):
    identity = []
    rowsNo = len(X)
    colsNo = len(X[0])
    if rowsNo != colsNo:
        print('Not squared matrix')
        return
    det = determinant(X)
    if det == 0:
        print('Matrix is not identity')
        return
    trans = transpsusa(X)
    for i in range(rowsNo):
        line = []
        for j in range(colsNo):
            d = (-1) ** (i + j) * determinant(cofactor(trans, i, j))
            inv = d * 1/det
            line.append(inv)
        identity.append(line)
    return identity

def meanSquareError(regressor, myinput, myoutput):
    error = 0.0
    for t1, t2 in zip(regressor.predict(myinput), myoutput):
        error += (t1 - t2) ** 2
    error = error / len(myoutput)
    return error
