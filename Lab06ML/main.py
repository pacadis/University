from math import sqrt
from sklearn.metrics import confusion_matrix
from numpy import log


# Performance for the multitarget regression
def calcMultiTargetRegression(realOutputs, computedOutputs):
    errorL1List = []
    errorL2List = []
    for real, computed in zip(realOutputs, computedOutputs):
        errorL1 = sum(abs(r - c) for r, c in zip(real, computed)) / len(real)
        errorL2 = sqrt(sum((r - c) ** 2 for r, c in zip(real, computed)) / len(real))

        print(len(real))

        errorL1List.append(errorL1)
        errorL2List.append(errorL2)

    print('Error (L1): ', errorL1List)
    print('Error (L2): ', errorL2List)


# Performenace for the multi class classification
def calcMultiClassClassification(realLabels, computedLabels, labels):
    # confusionMat = confusion_matrix(realLabels, computedLabels, labels)
    confusionMat = calcConfusion(realLabels, computedLabels, labels)
    print(confusionMat)

    # Accuracy
    correct = 0
    total = sum(i for label in confusionMat for i in label)
    for i in range(len(confusionMat)):
        correct += confusionMat[i][i]
    print("Accuracy :", correct / total)

    # Precision and Recall
    for i in range(len(labels)):
        computedCorrect = confusionMat[i][i]
        totalForLabel = sum(confusionMat[i])
        computedTotal = 0
        for j in range(len(labels)):
            computedTotal += confusionMat[j][i]
        print("Precision", labels[i], ":", computedCorrect / computedTotal)
        print("Recall", labels[i], ":", computedCorrect / totalForLabel)


def calcConfusion(realLabels, computedLabels, labels):
    confMat = []
    for _ in range(len(labels)):
        confMat.append([0] * len(labels))
    for i in range(len(realLabels)):
        real = 0
        computed = 0
        for j in range(len(labels)):
            if realLabels[i] == labels[j]:
                real = j
            if computedLabels[i] == labels[j]:
                computed = j
        confMat[real][computed] += 1
    return confMat


# Mean absolute loss function for regression problems
def mean_absolute_err_loss(realOutputs, computedOutputs):
    absErrorSum = 0
    for real, computed in zip(realOutputs, computedOutputs):
        absErrorSum += abs(computed - real);
    loss = absErrorSum / len(realOutputs)
    return loss


# Cross entropy loss function for binary classification
def log_loss(realLabels, computedLabels, labels):
    logSum = 0
    for real, computed in zip(realLabels, computedLabels):
        y = 0
        if (real == labels[0]):
            y = 1
        else:
            y = 0

        logSum += - y * log(computed[0]) - (1 - y) * log(computed[1])
    loss = logSum / len(realLabels)
    return loss


if __name__ == "__main__":
    # realOutputsRegression = [[3, 10.4], [9.5, 30.6], [4, 11], [5.1, 13.5], [6, 16], [7.2, 15], [2, 6], [1, 3.5]]
    # computedOutputsRegression = [[2, 10], [7, 15], [4.5, 8.6], [6, 14.30], [3, 7.40], [8, 14.5], [3, 6], [1.2, 4]]

    realOutputsRegression = [[1.1, 2, 1, 0.9, 3, 2.6, 2.3, 2], [3, 9.5, 4, 5.95, 6, 7.2, 2, 1]]
    computedOutputsRegression = [[1.05, 1.9, 2, 1.2, 2.96, 2.8, 2, 1.9], [2, 7, 4.5, 6, 3, 8, 3, 1.2]]

    realLabels = ['dog', 'dog', 'cat', 'crocodile', 'crocodile', 'cat', 'hippopotamus', 'dog']
    computedLabels = ['dog', 'dog', 'dog', 'hippopotamus', 'crocodile', 'hippopotamus', 'cat', 'dog']

    C = "Cat"
    F = "Fish"
    H = "Hen"
    realOutputsClassification = [C, C, C, C, C, C, F, F, F, F, F, F, F, F, F, F, H, H, H, H, H, H, H, H, H]
    computedOutputsClassification = [C, C, C, C, H, F, C, C, C, C, C, C, H, H, F, F, C, C, C, H, H, H, H, H, H]

    # computedOutputsClassificationPb = [[0.6,0.3,0.1],[0.5,0.4,0.1],[0.7, 0.3, 0],[0.7,0.1,0.2],[0, 0.3, 0.7],[0.2,0.6,0.2], [0.6,0.3,0.1],[0.7, 0.3, 0],[0.7,0.1,0.2],[0.6,0.3,0.1],[0.7, 0.3, 0],[0.6,0.3,0.1],[0, 0.2, 0.6],[0, 0.2, 0.6],[0.2,0.6,0.2],[0.2,0.6,0.2], [0.7,0.1,0.2],[0.6,0.3,0.1],[0.7, 0.3, 0],[0, 0.3, 0.7],[0, 0.2, 0.6],[0, 0.3, 0.7],[0, 0.2, 0.6],[0, 0.2, 0.6],[0, 0.3, 0.7]]

    regressionLossRealOutput = [3, 9.5, 4, 5.1, 6, 7.2, 2, 1]
    regressionLossComputedOutput = [2, 7, 4.5, 6, 3, 8, 3, 1.2]

    labels = ("apple", "peach")
    realProbLabels = ["apple", "apple", "peach", "peach"]
    computedProbLabels = [[0.7, 0.3], [0.5, 0.5], [0.6, 0.4], [0.9, 0.1]]

    print("Regression multi-target precision:")
    calcMultiTargetRegression(realOutputsRegression, computedOutputsRegression)
    print()
    # print("Clasification multi-class precision:")
    # calcMultiClassClassification(realOutputsClassification, computedOutputsClassification, [C, F, H])
    # print()
    # calcMultiClassClassification(realLabels, computedLabels, ['cat', 'crocodile', 'dog', 'hippopotamus'])
    # print()
    # # calcMultiClassClassification(realOutputsClassification, computedOutputsClassificationPb, realLabels)
    # print()
    # print("Mean absolute loss:")
    # print(mean_absolute_err_loss(regressionLossRealOutput, regressionLossComputedOutput))
    # print()
    # print("Cross entropy loss:")
    # print(log_loss(realProbLabels, computedProbLabels, labels))