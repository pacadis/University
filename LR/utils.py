import math
from cmath import sqrt

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
    avgLenSepala = average([trainInput[i][0] for i in range(len(trainInput))])
    devLenSepala = deviation([trainInput[i][0] for i in range(len(trainInput))], avgLenSepala)

    avgLatSepala = average([trainInput[i][1] for i in range(len(trainInput))])
    devLatSepala = deviation([trainInput[i][1] for i in range(len(trainInput))], avgLatSepala)

    avgLenPetala = average([trainInput[i][2] for i in range(len(trainInput))])
    devLenPetala = deviation([trainInput[i][2] for i in range(len(trainInput))], avgLenPetala)

    avgLatPetala = average([trainInput[i][3] for i in range(len(trainInput))])
    devLatPetala = deviation([trainInput[i][3] for i in range(len(trainInput))], avgLatPetala)

    #input normalization
    lenSepalaTrainInput = standardNormalization([trainInput[i][0] for i in range(len(trainInput))], avgLenSepala, devLenSepala)
    latSepalaTrainInput = standardNormalization([trainInput[i][1] for i in range(len(trainInput))], avgLatSepala, devLatSepala)
    lenPetalaTrainInput = standardNormalization([trainInput[i][2] for i in range(len(trainInput))], avgLenPetala, devLenPetala)
    latPetalaTrainInput = standardNormalization([trainInput[i][3] for i in range(len(trainInput))], avgLatPetala, devLatPetala)

    lenSepalaTestInput = standardNormalization([testInput[i][0] for i in range(len(testInput))], avgLenSepala, devLenSepala)
    latSepalaTestInput = standardNormalization([testInput[i][1] for i in range(len(testInput))], avgLatSepala, devLatSepala)
    lenPetalaTestInput = standardNormalization([testInput[i][2] for i in range(len(testInput))], avgLenPetala, devLenPetala)
    latPetalaTestInput = standardNormalization([testInput[i][3] for i in range(len(testInput))], avgLatPetala, devLatPetala)

    trainInput = [[lenSepalaTrainInput[i],
                   latSepalaTrainInput[i],
                   lenPetalaTrainInput[i],
                   latPetalaTrainInput[i]] for i in range(len(lenSepalaTrainInput))]

    testInput = [[lenSepalaTestInput[i],
                   latSepalaTestInput[i],
                   lenPetalaTestInput[i],
                   latPetalaTestInput[i]] for i in range(len(lenSepalaTestInput))]

    return trainInput, testInput

def accuracy(realLabels, computedLabels):
    return sum([1 if realLabels[i] == computedLabels[i] else 0 for i in range(len(realLabels))])/len(realLabels)