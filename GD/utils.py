from math import sqrt


def meanSquareError(regressor, myinput, myoutput):
    error = 0.0
    for t1, t2 in zip(regressor.predict(myinput), myoutput):
        error += (t1 - t2) ** 2
    error = error / len(myoutput)
    return error


def normalization(X):
    normalizedX = []
    for i in range(0, len(X)):
        normalizedX.append((X[i] - min(X)) / (max(X) - min(X)))
    return normalizedX

def unitVectorNormalization(x):
    m = 0
    for index in x:
        m += index ** 2;
    m = sqrt(m)
    for index in range(0, len(x)):
        x[index] /= m
    return x

def standardNormalization(x):
    #calculate average
    average = 0
    for i in range(0, len(x)):
        average += x[i]
    average /= len(x)

    #calculate deviation
    deviation = 0
    for i in range(0, len(x)):
        deviation += (x[i] - average) ** 2
    deviation /= len(x)
    deviation = sqrt(deviation)

    #normalization
    normalizatedX = [(value - average) / deviation for value in x]
    return normalizatedX

def executeNormalization(trainInput, testInput, trainOutput, testOutput):
    #output normalization
    trainOutput = unitVectorNormalization(trainOutput)
    testOutput = unitVectorNormalization(testOutput)

    #input normalization
    GDPTrainInputs = standardNormalization([trainInput[i][0] for i in range(len(trainInput))])
    GDPTestInputs = standardNormalization([testInput[i][0] for i in range(len(testInput))])
    FreedomTrainInputs = standardNormalization([trainInput[i][1] for i in range(len(trainInput))])
    FreedomTestInputs = standardNormalization([testInput[i][1] for i in range(len(testInput))])

    trainInput = [[GDPTrainInputs[i], FreedomTrainInputs[i]] for i in range(0, len(GDPTrainInputs))]
    testInput = [[GDPTestInputs[i], FreedomTestInputs[i]] for i in range(0, len(GDPTestInputs))]

    return trainInput, testInput, trainOutput, testOutput