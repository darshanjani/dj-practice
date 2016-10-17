'''
Created on 26 Aug 2016

@author: Darshan
'''
from pymongo import MongoClient
from _datetime import datetime

client = MongoClient()
db = client.blog
users = db.users
articles = db.articles

author = "darshan"
title = "This is a post on MongoDB"

article = {
    "title": title,
    "body": "MongoDB is easy to start and learn",
    "author": author,
    "tags": ["general", "mongodb", "user"],
    "section": "technology",
    "postDate": datetime.now()
}

if users.find_one({"username": author}):
    articles.insert_one(article)
else:
    raise ValueError("Author %s does not exist" % author)
