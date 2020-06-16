import np
from sklearn import linear_model

from MyLogisticRegression import MyLogisticRegression
from readData import read
from utils import executeNormalization, accuracy


def manual_regression(regressor, trainInput, trainOutput):
    regressor.fit(trainInput, trainOutput)

def tool_regression(regressor, trainInput, trainOutput):
    pass

def main():
    file = 'iris.data'

    inputs, output = read(file)

    #split 80/20
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [output[i] for i in trainSample]

    testInputs = [inputs[i] for i in testSample]
    testOutputs = [output[i] for i in testSample]

    trainInputs, testInputs = executeNormalization(trainInputs, testInputs)
    #trainInputs = [[trainInputs[i][0], trainInputs[i][1]] for i in range(len(trainInputs)]
    # inputs, output = executeNormalization(inputs, output)

    # print(trainInputs)
    # print(testInputs)

    # MANUAL REGRESSION
    setosaManualRegression = MyLogisticRegression()
    versicolorManualRegression = MyLogisticRegression()
    virginicaManualRegression = MyLogisticRegression()

    #setosa
    outputs = [1 if trainOutputs[i] == "Iris-setosa" else 0 for i in range(len(trainOutputs))]
    manual_regression(setosaManualRegression, trainInputs, outputs)
    setpred = setosaManualRegression.predict(testInputs)

    #versicolor
    outputs = [1 if trainOutputs[i] == "Iris-versicolor" else 0 for i in range(len(trainOutputs))]
    manual_regression(versicolorManualRegression, trainInputs, outputs)
    verpred = versicolorManualRegression.predict(testInputs)

    # virginica
    outputs = [1 if trainOutputs[i] == "Iris-virginica" else 0 for i in range(len(trainOutputs))]
    manual_regression(virginicaManualRegression, trainInputs, outputs)
    virpred = virginicaManualRegression.predict(testInputs)

    myLinearModel = linear_model.LogisticRegression(max_iter=1000)
    myLinearModel.fit(trainInputs, trainOutputs)
    skpred = myLinearModel.predict(testInputs)

    outputs = [[setpred[i], verpred[i], virpred[i]] for i in range(len(setpred))]
    # [[1,2,3], [2,3,4] ... ] 30x
    labels = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
    computebLabels = []
    print("COMPUTED         REAL        SKLEARN")
    for i in range(0, len(setpred)):
        computebLabels.append(labels[outputs[i].index(max(outputs[i]))])
        print(computebLabels[i], "      ", testOutputs[i], "        ", skpred[i])


    print("Manual Accuracy: ", accuracy(testOutputs, computebLabels))
    print("Tool Accuracy: ", accuracy(testOutputs, skpred))

main()