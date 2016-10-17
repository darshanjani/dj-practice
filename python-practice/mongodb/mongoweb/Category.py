from pymongo import MongoClient
from bson import ObjectId

category = {
    '_id': ObjectId("6a5b1476238d3b4dd5000048"),
    'slug': "gardening-tools",
    'name': "Gardening Tools",
    'description': "Gardening gadgets galore!",
    'parent_id': ObjectId("55804822812cb336b78728f9"),
    'ancestors': [
        {
            'name': "Home",
            '_id': ObjectId("558048f0812cb336b78728fa"),
            'slug': "home"
        }, {
            'name': "Outdoors",
            '_id': ObjectId("55804822812cb336b78728f9"),
            'slug': "outdoors"
        }
    ]
}

client = MongoClient()
db = client.get_database('tutorial')
categories = db.get_collection('categories')
categories.insert_one(category)
