from pymongo import MongoClient
from bson.objectid import ObjectId

client = MongoClient()
db = client.get_database("tutorial")
users = db.get_collection("users")
print("Connected to database")

smith_id = users.insert_one({"last_name": "smith", "age": 40})
jones_id = users.insert_one({"last_name": "jones", "age": 30})

print(list(users.find({"age": {"$gte": 30}})))
print(list(users.find({"last_name": "smith"})))
for user in users.find({"age": {"$gt": 20}}):
    # print(user['last_name'])
    print(user['_id'].generation_time)

users.update_many({"last_name": "smith"}, {"$set": {"city": "Chicago"}})

users.delete_many({"age": {"$gte": 40}})

users.drop()

print(client.database_names())
admindb = client.get_database("admin")
print(admindb.command({"listDatabases": 1}))