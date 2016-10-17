from pymongo import MongoClient
import random
from datetime import datetime

VIEW_PRODUCT = 0 # action type constants
ADD_TO_CART = 1
CHECKOUT = 2
PURCHASE = 3

client = MongoClient()
db = client.get_database("tutorial")
user_reviews = db.get_collection("user_reviews")

user_reviews.drop()

user_reviews = db.create_collection('user_reviews');

for n in range(500):
    doc = {
        'username': 'kbanker',
        'action_code': random.randrange(4),
        'time': datetime.utcnow(),
        'n': n
    }
    user_reviews.insert_one(doc)

user_reviews.create_index("time", expireAfterSeconds=30)