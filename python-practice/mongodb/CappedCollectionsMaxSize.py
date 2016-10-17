from pymongo import MongoClient
import random
from datetime import datetime

VIEW_PRODUCT = 0 # action type constants
ADD_TO_CART = 1
CHECKOUT = 2
PURCHASE = 3

client = MongoClient()
db = client.get_database("tutorial")
user_actions = db.get_collection("user_actions")

user_actions.drop()

user_actions = db.create_collection('user_actions', capped=True, size=16384, max=100);

for n in range(500):
    doc = {
        'username': 'kbanker',
        'action_code': random.randrange(4),
        'time': datetime.today().isoformat(),
        'n': n
    }
    user_actions.insert_one(doc)
