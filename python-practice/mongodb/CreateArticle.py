'''
Created on 26 Aug 2016

@author: Darshan
'''
from pymongo import MongoClient

client = MongoClient()
db = client.blog
users = db.users
articles = db.articles

author="darshan"

article = {
    "title": "This is my first post",
    "body": "This is longer text of body which is a blog text",
    "author": author,
    "tags": ["general", "first", "admin"]
}

if users.find_one({"username" : author}):
    articles.insert_one(article)
else:
    raise ValueError("Author %s does not exist" % author)
