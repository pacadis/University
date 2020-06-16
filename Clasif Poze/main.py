from np.magic import np
from sklearn import neural_network
from sklearn.metrics import confusion_matrix

from readData import readFiles
from utils import executeNormalization


def training(classifier, trainInputs, trainOutputs):
    # step4: training the classifier
    # identify (by training) the classification model
    classifier.fit(trainInputs, trainOutputs)

def classification(classifier, testInputs):
    # step5: testing (predict the labels for new inputs)
    # makes predictions for test data
    computedTestOutputs = classifier.predict(testInputs)

    return computedTestOutputs

def evalMultiClass(realLabels, computedLabels, labelNames):

    confMatrix = confusion_matrix(realLabels, computedLabels)
    acc = sum([confMatrix[i][i] for i in range(len(labelNames))]) / len(realLabels)
    precision = {}
    recall = {}
    for i in range(len(labelNames)):
        precision[labelNames[i]] = confMatrix[i][i] / sum([confMatrix[j][i] for j in range(len(labelNames))])
        recall[labelNames[i]] = confMatrix[i][i] / sum([confMatrix[i][j] for j in range(len(labelNames))])
    return acc, precision, recall, confMatrix

def main():
    inputs, outputs = readFiles()
    # np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]

    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    trainInputs, testInputs = executeNormalization(trainInputs, testInputs)

    labels = ['normal', 'sepia']

    #relu function f(x) = x
    #solver sgd - stochastic gradient descent

    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5, ), activation='relu', max_iter=1000, solver='sgd', verbose=10, random_state=1, learning_rate_init=.1)
    print(classifier.hidden_layer_sizes)

    # train
    training(classifier, trainInputs, trainOutputs)

    # predict
    predicted = classification(classifier, testInputs)
    print(predicted)
    print(testOutputs)
    a, p, r, c = evalMultiClass(np.array(testOutputs), predicted, labels)
    print("Accuracy: ", a)
    print("Precision: ", p)
    print("Recall: ", r)
    print(classifier.hidden_layer_sizes)


if __name__ == '__main__':
    main()