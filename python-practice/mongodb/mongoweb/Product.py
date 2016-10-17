from datetime import date
from pymongo import MongoClient

from bson import ObjectId

product = {
    '_id': ObjectId("4c4b1476238d3b4dd5003981"),
    'slug': "wheelbarrow-9092",
    'sku': "9092",
    'name': "Extra Large Wheelbarrow",
    'description': "Heavy duty wheelbarrow...",
    'details': {
        'weight': 47,
        'weight_units': "lbs",
        'model_num': 4039283402,
        'manufacturer': "Acme",
        'color': "Green"
    },
    'total_reviews': 4,
    'average_review': 4.5,
    'pricing': {
        'retail': 589700,
        'sale': 489700,
    },
    'price_history': [
        {
            'retail': 529700,
            'sale': 429700,
            'start': date(2010, 4, 1).isoformat(),
            'end': date(2010, 4, 8).isoformat()
        },
        {
            'retail': 529700,
            'sale': 529700,
            'start': date(2010, 4, 9).isoformat(),
            'end': date(2010, 4, 16).isoformat()
        },
    ],
    'primary_category': ObjectId("6a5b1476238d3b4dd5000048"),
    'category_ids': [
        ObjectId("6a5b1476238d3b4dd5000048"),
        ObjectId("6a5b1476238d3b4dd5000049")
    ],
    'main_cat_id': ObjectId("6a5b1476238d3b4dd5000048"),
    'tags': ["tools", "gardening", "soil"],
}

client = MongoClient()
db = client.get_database('tutorial')
products = db.get_collection('products')
products.insert_one(product)
