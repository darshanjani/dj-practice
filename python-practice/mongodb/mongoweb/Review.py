from datetime import date

from bson import ObjectId
from pymongo import MongoClient

review = {
    '_id': ObjectId("4c4b1476238d3b4dd5000041"),
    'product_id': ObjectId("4c4b1476238d3b4dd5003981"),
    'date': date(2010, 5, 7).isoformat(),
    'title': "Amazing",
    'text': "Has a squeaky wheel, but still a darn good wheelbarrow.",
    'rating': 4,
    'user_id': ObjectId("4c4b1476238d3b4dd5000042"),
    'username': "dgreenthumb",
    'helpful_votes': 3,
    'voter_ids': [
        ObjectId("4c4b1476238d3b4dd5000033"),
        ObjectId("7a4f0376238d3b4dd5000003"),
        ObjectId("92c21476238d3b4dd5000032")
    ]
}

client = MongoClient()
db = client.get_database('tutorial')
reviews = db.get_collection('review')
reviews.insert_one(review)
