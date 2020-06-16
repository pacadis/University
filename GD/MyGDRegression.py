class MySGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []


    def fit(self, x, y, learning_rate = 0.005, nrEpochs = 1000):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]
        for epoch in range(nrEpochs):
            errors = [0.0 for _ in range(len(x[0]) + 1)]
            for i in range(len(x)):
                guess = self.eval(x[i])
                error = guess - y[i]
                for j in range(0, len(x[0])):
                    errors[j] += error * x[i][j]
                errors[-1] += error
            #update coefficients
            for i in range(0, len(x[0]) + 1):
                self.coef_[i] -= (learning_rate * (errors[i]/len(x)))

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def eval(self, x):
        y = self.coef_[-1]
        for j in range(len(x)):
            y += self.coef_[j] * x[j]
        return y

    def predict(self, x):
        computed = [self.eval(t) for t in x]
        return computed