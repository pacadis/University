import csv

def readData(file, input1, input2, output):
    data = []
    dataNames = []
    with open(file) as csv_file:
        reader = csv.reader(csv_file, delimiter=',')
        linesCount = 0
        for row in reader:
            if linesCount == 0:
                dataNames = row
            else:
                data.append(row)
            linesCount += 1
    selectedVar1 = dataNames.index(input1)
    selectedVar2 = dataNames.index(input2)
    inputs = [[float(data[i][selectedVar1]), float(data[i][selectedVar2])] for i in range(len(data))]
    selectedOutput = dataNames.index(output)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs