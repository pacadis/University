from math import sqrt


def abs_dif_regression(realOutputs, computedOutputs):
    result = []
    i = 0

    while i < len(realOutputs):
        result.append(sum(abs(real - computed) for real, computed in zip(realOutputs[i], computedOutputs[i])) / len(realOutputs[0]))
        i += 1

    return result


def dif_squares_regression(realOutputs, computedOutputs):
    result = []
    i = 0

    while i < len(realOutputs):
        result.append(sqrt(sum(((real - computed) ** 2) for real, computed in zip(realOutputs[i], computedOutputs[i])) / len(realOutputs[0])))
        i += 1

    return result

def main():
    realOutputs = [[1.1,  2, 1, 0.9, 3, 2.6, 2.3, 2], [3, 9.5, 4, 5.95, 6, 7.2, 2, 1]]
    computedOutputs = [[1.05, 1.9, 2, 1.2, 2.96, 2.8, 2, 1.9], [2, 7, 4.5, 6, 3, 8, 3, 1.2]]
    print('The absolute difference(MAE - Mean Absolute Error):    ' + str(abs_dif_regression(realOutputs, computedOutputs)))
    print('The squared difference(RMSE - Root Mean Square Error): ' + str(dif_squares_regression(realOutputs, computedOutputs)))


if __name__ == '__main__':
    main()

