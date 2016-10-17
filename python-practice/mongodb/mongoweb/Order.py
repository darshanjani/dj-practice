from bson import ObjectId
from pymongo import MongoClient

order = {
    '_id': ObjectId("6a5b1476238d3b4dd5000048"),
    'user_id': ObjectId("4c4b1476238d3b4dd5000001"),
    'state': "CART",
    'line_items': [
        {
            '_id': ObjectId("4c4b1476238d3b4dd5003981"),
            'sku': "9092",
            'name': "Extra Large Wheelbarrow",
            'quantity': 1,
            'pricing': {
                'retail': 5897,
                'sale': 4897,
            }
        },
        {
            '_id': ObjectId("4c4b1476238d3b4dd5003982"),
            'sku': "10027",
            'name': "Rubberized Work Glove, Black",
            'quantity': 2,
            'pricing': {
                'retail': 1499,
                'sale': 1299
            }
        }
    ],
    'shipping_address': {
        'street': "588 5th Street",
        'city': "Brooklyn",
        'state': "NY",
        'zip': 11215
    },
    'sub_total': 6196
}

client = MongoClient()
db = client.get_database('tutorial')
orders = db.get_collection('orders')
orders.insert_one(order)
