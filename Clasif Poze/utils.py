import math

def average(x):
    # calculate average
    avg = 0
    for i in range(0, len(x)):
        avg += x[i]
    avg /= len(x)
    return avg

def deviation(x, avg):
    # calculate deviation
    dev = 0
    for i in range(0, len(x)):
        dev += (x[i] - avg) ** 2
    dev /= len(x)
    dev = math.sqrt(dev)
    return dev

def standardNormalization(x, avg, dev):
    #normalization
    normalizatedX = [(value - avg) / dev for value in x]
    return normalizatedX

def executeNormalization(trainInput, testInput):
    #output normalization
    redavg = average([trainInput[i][0] for i in range(len(trainInput))])
    reddev = deviation([trainInput[i][0] for i in range(len(trainInput))], redavg)

    greenavg = average([trainInput[i][1] for i in range(len(trainInput))])
    greendev = deviation([trainInput[i][1] for i in range(len(trainInput))], greenavg)

    blueavg = average([trainInput[i][2] for i in range(len(trainInput))])
    bluedev = deviation([trainInput[i][2] for i in range(len(trainInput))], blueavg)


    #input normalization
    redTrainInput = standardNormalization([trainInput[i][0] for i in range(len(trainInput))], redavg, reddev)
    greenTrainInput = standardNormalization([trainInput[i][1] for i in range(len(trainInput))], greenavg, greendev)
    blueTrainInput = standardNormalization([trainInput[i][2] for i in range(len(trainInput))], blueavg, bluedev)

    redTestInput = standardNormalization([testInput[i][0] for i in range(len(testInput))], redavg, reddev)
    greenTestInput = standardNormalization([testInput[i][1] for i in range(len(testInput))], greenavg, greendev)
    blueTestInput = standardNormalization([testInput[i][2] for i in range(len(testInput))], blueavg, bluedev)

    trainInput = [[redTrainInput[i],
                   greenTrainInput[i],
                   blueTrainInput[i]] for i in range(len(redTrainInput))]

    testInput = [[redTestInput[i],
                   greenTestInput[i],
                   blueTestInput[i]] for i in range(len(redTestInput))]

    return trainInput, testInput