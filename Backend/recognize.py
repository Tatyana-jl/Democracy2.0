import requests
import matplotlib.pyplot as plt
import json
from PIL import Image
from io import BytesIO
import argparse
import urllib
def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-image', '--image', required=True)
    parser.add_argument('-sub_key', '--sub_key', required=False, default='c3aa71ab19bd40fcbe8459e7a18bc080')
    # Add your Computer Vision subscription key and endpoint to your environment variables.
    arguments = vars(parser.parse_args())
    image_url = arguments['image']
    subscription_key = arguments['sub_key']
    endpoint = 'https://northeurope.api.cognitive.microsoft.com/'
    analyze_url = endpoint + "vision/v2.1/analyze"
    headers = {'Ocp-Apim-Subscription-Key': subscription_key}
    params = {'visualFeatures': 'Categories,Description,Color'}
    data = {'url': image_url}
    response = requests.post(analyze_url, headers=headers, params=params, json=data)
    response.raise_for_status()
    # The 'analysis' object contains various fields that describe the image. The most
    # relevant caption for the image is obtained from the 'description' property.
    analysis = response.json()
    print(json.dumps(response.json()))
    image_caption = analysis["description"]["captions"][0]["text"].capitalize()
    # Display the image and overlay it with the caption.
    # image = Image.open(BytesIO(requests.get(image_url).content))
    # plt.imshow(image)
    # plt.axis("off")
    # _ = plt.title(image_caption, size="x-large", y=-0.1)
    # plt.show()
if __name__=='__main__':
    main()
