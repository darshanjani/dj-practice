from pymongo import MongoClient
from bson.json_util import dumps
import json

client = MongoClient()
db = client.test
# cursor = db.restaurants.find()
# cursor = db.restaurants.find({"borough": "Manhattan"})
# cursor = db.restaurants.find({"address.zipcode": "10075"})
# cursor = db.restaurants.find({"grades.grade": "B"})
# cursor = db.restaurants.find({"grades.score": {"$gt": 30}})
# cursor = db.restaurants.find({"cuisine": "Italian", "address.zipcode": "10075"})
cursor = db.restaurants.find(
    {"$or": [{"cuisine": "Italian"}, {"address.zipcode": "10075"}]})
# print(cursor)
print(cursor.count())
# print(cursor.explain())
# js = dumps(cursor)
# dj = json.loads(js)
# print(type(dj))
# for a in dj:
#     pass
#     # print(type(a))
#     # print(a.borough)
d = cursor.next()
if d:
    print(d)
    print(d['address']['coord'][0])
    for k, v in d.items():
        print ('key = value ', k, v)
        print(type(v))
        if (isinstance(v, dict)):
            for k1, v1 in v.items():
                print('key2 = value2 ', k1, v1)
    # print(dj['cusine'])
    # print(dj)
# for document in cursor:
#     print(document)
