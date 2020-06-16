from os.path import isfile, join

import cv2
import os

def getPixelsAverageColorValue(path):
    image = cv2.imread(path)
    w, h, _ = image.shape
    colors = [0, 0, 0]
    for i in range(0, w):
        for j in range(0, h):
            colors += image[i, j]
    total = w * h
    colors[0] /= total
    colors[1] /= total
    colors[2] /= total

    return colors[0], colors[1], colors[2]

def readFiles():
    inputs = []
    outputs = []
    # get normal files
    newpath = "normal"
    onlyfiles = [f for f in os.listdir(newpath) if isfile(join(newpath, f))]

    [inputs.append(getPixelsAverageColorValue(newpath + '/' + image)) for image in onlyfiles]
    [outputs.append('normal') for _ in onlyfiles]

    # get sepia files
    newpath = "sepia"
    onlyfiles = [f for f in os.listdir(newpath) if isfile(join(newpath, f))]

    [inputs.append(getPixelsAverageColorValue(newpath + '/' + image)) for image in onlyfiles]
    [outputs.append('sepia') for _ in onlyfiles]

    return inputs, outputs