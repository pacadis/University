from utils import *

class Regression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = [0.0, 0.0]

    def fit(self, x, y):
        pass

        X = [[1, x[i][0], x[i][1]] for i in range(0, len(x))]
        Y = [[y[i]] for i in range(0, len(y))]

        beta = transpsusa(X)
        beta = inmultire(beta, X)
        beta = inversa(beta)
        beta = inmultire(beta, transpsusa(X))
        beta = inmultire(beta, Y)

        w0 = beta[0][0]
        w1 = beta[1][0]
        w2 = beta[2][0]

        self.intercept_, self.coef_ = w0, [w1, w2]

    def predict(self, x):
        if (isinstance(x[0], list)):
            return [self.intercept_ + self.coef_[0] * val[0] + self.coef_[1] * val[1] for val in x]
        else:
            return self.intercept_ + self.coef_[0] * x[0] + self.coef_[1] * x[1]