import os
import np
import matplotlib.pyplot as plt

from networkx.drawing.tests.test_pylab import plt
from sklearn import *
from sklearn.metrics import mean_squared_error
from reader import readData
from regression import Regression
from mpl_toolkits import mplot3d
from utils import meanSquareError

def tool_regression(trainInputs, trainOutputs):
    regressor = linear_model.LinearRegression()
    regressor.fit(trainInputs, trainOutputs)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('The learnt model is: f(x) = ', w0, ' + ', w1 , ' * x1' , ' + ', w2, ' * x2')
    return regressor

def manual_regression(trainInputs, trainOutputs):
    regressor = Regression()
    regressor.fit(trainInputs, trainOutputs)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')
    return regressor

def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()


def plotData(trainC, trainF, trainO, coef, name):
    noOfPoints = 1000
    xref = []
    val = min(trainC)
    step = (max(trainC) - min(trainF)) / noOfPoints
    xref1 = []
    val1 = min(trainF)
    step1 = (max(trainF) - min((trainF))) / noOfPoints
    for i in range(1, noOfPoints):
        xref.append(val)
        xref1.append(val1)
        val += step
        val1 += step1
    yref = [[coef[0] + coef[1] * x1 + coef[2] * y1] for x1, y1 in zip(xref, xref1)]

    ax = plt.axes(projection='3d')
    ax.scatter3D(trainC, trainF, trainO, c='b', marker='o')
    ax.plot_surface(np.array(xref), np.array(xref1), np.array(yref), color='green')
    ax.set_xlabel('Capital')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('Happiness')
    plt.title(name)
    plt.show()

def predictedPlot(capital, freedom, output, computemOutput, name):
    ax = plt.axes(projection='3d')
    ax.scatter3D(capital, freedom, output, c='b', marker='o')
    ax.scatter3D(capital, freedom, computemOutput, c='g', marker='^')
    ax.set_xlabel('Capital')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('Happiness')
    plt.title(name)
    plt.show()

def main():
    currDir = os.getcwd()
    path = os.path.join(currDir, '2017.csv')

    input, output = readData(path, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')

    # split in 80/20 percent
    np.random.seed(5)
    indexes = [i for i in range(len(input))]
    trainSample = np.random.choice(indexes, int(0.8 * len(input)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [input[i] for i in trainSample]
    trainOutputs = [output[i] for i in trainSample]

    testInputs = [input[i] for i in testSample]
    testOutputs = [output[i] for i in testSample]

    print('=== SKLEARN MODEL ===')
    tool_regressor = tool_regression(trainInputs, trainOutputs)
    # print('Tool predict ' + str(tool_regressor.predict(testInputs)))
    print('\n\n=== MY MODEL ===')
    manual_regressor = manual_regression(trainInputs, trainOutputs)
    # print('Manual predict ' + str(manual_regressor.predict(testInputs)))

    print('\n\n===Performance===')
    print('Tool prediction error:   ', mean_squared_error(testOutputs, tool_regressor.predict(testInputs)))
    print('Manual prediction error: ', meanSquareError(manual_regressor, testInputs, testOutputs))

    plotDataHistogram([input[i][0] for i in range(0, len(trainInputs))], 'capita GDP')
    plotDataHistogram([input[i][1] for i in range(0, len(trainInputs))], 'freedom')
    plotDataHistogram(trainOutputs, 'Happiness score')

    plotData([trainInputs[i][0] for i in range(0, len(trainInputs))],
             [trainInputs[i][1] for i in range(0, len(trainOutputs))], trainOutputs,
             [manual_regressor.intercept_, manual_regressor.coef_[0], manual_regressor.coef_[1]], 'TRAIN BASED ON LEARNT MODEL')

    plotData([testInputs[i][0] for i in range(0, len(testInputs))],
             [testInputs[i][1] for i in range(0, len(testInputs))], testOutputs,
             [manual_regressor.intercept_, manual_regressor.coef_[0], manual_regressor.coef_[1]], 'TEST BASED ON LEARNT MODEL')

    predictedPlot([testInputs[i][0] for i in range(0, len(testInputs))],
             [testInputs[i][1] for i in range(0, len(testInputs))], testOutputs,
            manual_regressor.predict(testInputs), 'PREDICTED BASED ON LEARNT MODEL')

if __name__ == '__main__':
    main()
