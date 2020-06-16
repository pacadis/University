import os
from os.path import isfile, join

import PIL
from PIL import Image


def scale_image(input_image_path,
                output_image_path,
                width=None,
                height=None
                ):
    original_image = Image.open(input_image_path)
    w, h = original_image.size
    print('The original image size is {wide} wide x {height} '
          'high'.format(wide=w, height=h))
    if width and height:
        max_size = (width, height)
    elif width:
        max_size = (width, h)
    elif height:
        max_size = (w, height)
    else:
        # No width or height specified
        raise RuntimeError('Width or height required!')
    original_image.thumbnail(max_size, Image.ANTIALIAS)
    original_image.save(output_image_path)
    scaled_image = Image.open(output_image_path)
    width, height = scaled_image.size
    print('The scaled image size is {wide} wide x {height} '
          'high'.format(wide=width, height=height))

if __name__ == '__main__':
    newpath = 'normal'
    onlyfiles = [f for f in os.listdir(newpath) if isfile(join(newpath, f))]
    for image in onlyfiles:
        scale_image(input_image_path='normal/' + image,
                    output_image_path='normal/' + image,
                    width=100, height=100)
    newpath = 'sepia'
    onlyfiles = [f for f in os.listdir(newpath) if isfile(join(newpath, f))]
    for image in onlyfiles:
        scale_image(input_image_path='sepia/' + image,
                    output_image_path='sepia/' + image,
                    width=20, height=20)