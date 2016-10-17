from bson import ObjectId
from pymongo import MongoClient

user = {
    '_id': ObjectId("4c4b1476238d3b4dd5000001"),
    'username': "kbanker",
    'email': "kylebanker@gmail.com",
    'first_name': "Kyle",
    'last_name': "Banker",
    'hashed_password': "bd1cfa194c3a603e7186780824b04419",
    'addresses': [
        {
            'name': "home",
            'street': "588 5th Street",
            'city': "Brooklyn",
            'state': "NY",
            'zip': 11215
        },
        {
            'name': "work",
            'street': "1 E. 23rd Street",
            'city': "New York",
            'state': "NY",
            'zip': 10010
        }
    ],
    'payment_methods': [
        {
            'name': "VISA",
            'payment_token': "43f6ba1dfda6b8106dc7"
        }
    ]
}

client = MongoClient()
db = client.get_database('tutorial')
users = db.get_collection('users')
users.insert_one(user)
