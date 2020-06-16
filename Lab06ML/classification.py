def classification(realLabels, computedLabels, positive, negative):
    TP = sum(
        [1 if (realLabels[i] == positive and computedLabels[i] == positive) else 0 for i in range(len(realLabels))])
    FP = sum(
        [1 if (realLabels[i] in negative and computedLabels[i] == positive) else 0 for i in range(len(realLabels))])
    TN = sum(
        [1 if (realLabels[i] in negative and computedLabels[i] in negative) else 0 for i in range(len(realLabels))])
    FN = sum(
        [1 if (realLabels[i] == positive and computedLabels[i] in negative) else 0 for i in range(len(realLabels))])

    return TP, FP, TN, FN


def accuracy(realLabels, computedLabels):
    return sum([1 if realLabels[i] == computedLabels[i] else 0 for i in range(len(realLabels))])


def calculatePrecision(TP, FP):
    return TP / (TP + FP)


def calculateRecall(TP, FN):
    return TP / (TP + FN)

def probabilities_classification(labels, probabilities):
    computedLabels = []
    for prob in probabilities:
        computedLabels.append(labels[prob.index(max(prob))])
    return computedLabels

def main():
    bl, got, cp = 'The Blacklist', 'Game of Thrones', 'La Casa de Papel'
    realLabels = [bl, got, bl, cp, cp, bl, got, got, bl, cp, cp, cp, bl, got, bl, bl, bl, bl, got, cp]
    computedLabels = [bl, cp, bl, cp, cp, bl, got, cp, bl, cp, cp, bl, got, got, got, bl, bl, bl, got, cp]

    print('Accuracy is ' + str(accuracy(realLabels, computedLabels)) + '%')

    print('\n=== The Blacklist ===')
    TP, FP, TN, FN = classification(realLabels, computedLabels, bl, [got, cp])
    blPosPrecision = calculatePrecision(TP, FP)
    blNegPrecision = calculatePrecision(TN, FN)
    blPosRecall = calculatePrecision(TP, FN)
    blNegRecall = calculatePrecision(TN, FP)
    print('Positive Precision: ' + str(blPosPrecision))
    print('Negative Precision: ' + str(blNegPrecision))
    print('Positive Recall: ' + str(blPosRecall))
    print('Negative Recall: ' + str(blNegRecall))

    print('\n=== Game of Thrones ===')
    TP, FP, TN, FN = classification(realLabels, computedLabels, got, [bl, cp])
    gotPosPrecision = calculatePrecision(TP, FP)
    gotNegPrecision = calculatePrecision(TN, FN)
    gotPosRecall = calculatePrecision(TP, FN)
    gotNegRecall = calculatePrecision(TN, FP)
    print('Positive Precision: ' + str(gotPosPrecision))
    print('Negative Precision: ' + str(gotNegPrecision))
    print('Positive Recall: ' + str(gotPosRecall))
    print('Negative Recall: ' + str(gotNegRecall))

    print('\n=== La Casa de Papel ===')
    TP, FP, TN, FN = classification(realLabels, computedLabels, cp, [got, bl])
    cpPosPrecision = calculatePrecision(TP, FP)
    cpNegPrecision = calculatePrecision(TN, FN)
    cpPosRecall = calculatePrecision(TP, FN)
    cpNegRecall = calculatePrecision(TN, FP)
    print('Positive Precision: ' + str(cpPosPrecision))
    print('Negative Precision: ' + str(cpNegPrecision))
    print('Positive Recall: ' + str(cpPosRecall))
    print('Negative Recall: ' + str(cpNegRecall))

    labels = [bl, got, cp]
    probabilities = [[0.7, 0.2, 0.1], [0.2, 0.3, 0.5], [0.2, 0.2, 0.6], [0.1, 0.8, 0.1], [0.1, 0.6, 0.3], [0.4, 0.3, 0.3]]
    print('\n\nComputed Labels based on probabilites: ' + str(probabilities_classification(labels, probabilities)))

if __name__ == '__main__':
    main()
