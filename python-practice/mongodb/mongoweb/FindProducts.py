from pymongo import MongoClient

client = MongoClient()
db = client.get_database('tutorial')
products = db.get_collection('products')
categories = db.get_collection('categories')

product = products.find_one({"slug": "wheelbarrow-9092"})
category = categories.find({'_id': {'$in': product['category_ids']}})
print(list(category))
