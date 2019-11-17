import requests
import json
import argparse
import base64
​
def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-image', '--image', required=True)
    parser.add_argument('-sub_key', '--sub_key', required=False, default='c3aa71ab19bd40fcbe8459e7a18bc080')
​
    arguments = vars(parser.parse_args())
    image = arguments['image']
    subscription_key = arguments['sub_key']
​
    image = base64.b64decode(image)
    endpoint = 'https://northeurope.api.cognitive.microsoft.com/'
    analyze_url = endpoint + "vision/v2.1/analyze"
​
    headers = {'Ocp-Apim-Subscription-Key': subscription_key,
               'Content-Type': 'application/octet-stream'}
    params = {'visualFeatures': 'Categories,Description,Adult'}
    # data = {'url': image_url}
    response = requests.post(analyze_url, headers=headers,
                             params=params, data=image) #json=data)
    response.raise_for_status()
​
    analysis = response.json()
​
    tags = analysis['description']['tags']
    class_dict = {
        'street light': 'lighting',
        'dark': 'lighting',
        'light': 'lighting',
        'road surface': 'road',
        'asphalt': 'road',
        'path': 'road',
        'tree': 'landscaping',
        'plant': 'landscaping',
        'dirt': 'landscaping',
        'water': 'plumbing'
                  }
    result_dict = {
        'classes': [],
        'spam trigger': False,
        'inappropriate image': False
    }
    result_dict = check_adult_score(analysis['adult']['adultScore'], result_dict)
    result_dict['classes'] = define_class(tags, class_dict)
    result_dict = check_for_spam(result_dict['classes'], result_dict)
​
    app_json = json.dumps(result_dict)
    print(app_json)
​
​
def check_adult_score(adult_score, result_dict):
    if adult_score >= 0.1:
        result_dict['inappropriate image'] = True
    return result_dict
​
​
def define_class(tags, class_dict):
    classes = []
    for tag in tags:
        if tag in class_dict.keys():
            classes.append(class_dict[tag])
    return classes
​
​
def check_for_spam(classes, result_dict):
    if len(classes) == 0:
        result_dict['spam trigger'] = True
    return result_dict
​
​
if __name__ == '__main__':
    main()
