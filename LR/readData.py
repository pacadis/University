def read(file):
    file1 = open('iris.data', 'r')

    inputs = []
    output = []

    while True:
        line = file1.readline()

        if line:
            array = line.split(",")
            inputs.append([float(array[0]), float(array[1]), float(array[2]), float(array[3])])
            output.append(array[4].split('\n')[0])
        else:
            break

    file1.close();
    return inputs, output