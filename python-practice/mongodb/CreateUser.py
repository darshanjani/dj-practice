'''
Created on 26 Aug 2016

@author: Darshan
'''
from pymongo import MongoClient

client = MongoClient()
db = client.blog
users = db.users

users.insert_one({
    "username": "darshan",
    "password": "jani",
    "lang": "EN"
})

user = users.find_one()
print(user)