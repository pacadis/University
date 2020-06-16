import os
import np
from sklearn import linear_model
from sklearn.metrics import mean_squared_error

from MyGDRegression import MySGDRegression
from read import readData
from utils import meanSquareError, executeNormalization


def tool_regression(trainInputs, trainOutputs):
    regressor = linear_model.SGDRegressor(alpha=0.005, max_iter=1000, average=len(trainInputs))
    regressor.fit(trainInputs, trainOutputs)
    w0, w1, w2 = regressor.intercept_[0], regressor.coef_[0], regressor.coef_[1]
    print('The learnt model is: f(x) = ', w0, ' + ', w1 , ' * x1' , ' + ', w2, ' * x2')
    return regressor

def manual_regression(trainInputs, trainOutputs):
    regressor = MySGDRegression()
    regressor.fit(trainInputs, trainOutputs)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')
    return regressor

def main():
    currDir = os.getcwd()
    path = os.path.join(currDir, 'data.csv')

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

    #data normalization
    trainInputs, testInputs, trainOutputs, testOutputs = executeNormalization(trainInputs, testInputs, trainOutputs, testOutputs)

    #tool univariate
    GDPTrainInputs = [trainInputs[0] for _ in range(len(trainInputs))]
    GDPTestInputs = [testInputs[0] for _ in range(len(testInputs))]

    print("         UNIVARIATE")
    print("SKLEARN REGRESSION")
    regressorSklearnUni = linear_model.SGDRegressor(alpha=0.005, max_iter=1000, average=len(trainInputs))
    regressorSklearnUni.fit(GDPTrainInputs, trainOutputs)
    w = [regressorSklearnUni.intercept_[0], regressorSklearnUni.coef_[0]]
    print("Learnt model is: f(x) = " + str(w[0]) + " + " + str(w[1]) + " * x")

    print("MANUAL REGRESSION")
    regressorMySGDRegression = MySGDRegression()
    regressorMySGDRegression.fit(GDPTrainInputs, trainOutputs)
    w = [regressorMySGDRegression.intercept_, regressorMySGDRegression.coef_[0]]
    print("Learnt model is: f(x) = " + str(w[0]) + " + " + str(w[1]) + " * x")

    print("\n\n         BIVARIATE")
    print("SKLEARN REGRESSION")
    toolRegression = tool_regression(trainInputs, trainOutputs)
    print("MANUAL REGRESSION")
    manual_regressor = manual_regression(trainInputs, trainOutputs)



    print("\n\n ERRORS")
    print("1.TOOL UNIVARIATE ERROR:   ", mean_squared_error(testOutputs, toolRegression.predict(GDPTestInputs)))
    print("2.MANUAL UNIVARIATE ERROR: ", meanSquareError(manual_regressor, GDPTestInputs, testOutputs))
    print("3.TOOL BIVARIATE ERROR:    ", mean_squared_error(testOutputs, toolRegression.predict(testInputs)))
    print("4.MANUAL BIVARIATE ERROR:  ", meanSquareError(manual_regressor, testInputs, testOutputs));



if __name__ == '__main__':
    main()