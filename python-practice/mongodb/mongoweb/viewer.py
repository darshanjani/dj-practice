from flask import Flask, render_template, request
from pymongo import MongoClient

from mongodb.TwitterConstants import *

app = Flask(__name__)

client = None
db = None
tweets = None


def find_tweets(tag=None):
    client = MongoClient(host=DATABASE_HOST, port=DATABASE_PORT);
    db = client.get_database(DATABASE_NAME)
    tweets = db.get_collection(COLLECTION_NAME)
    selector = {}
    if tag is not None:
        selector = {'tags': tag}
    print("Finding this: ", selector)
    return tweets.find(selector)
# .sort({"id": -1})


@app.route("/")
def index():
    tag = request.args.get('tag')
    tweets = find_tweets(tag)
    tweets = list(tweets)
    print("Found Tweets: ", tweets)
    return render_template('tweetsBootstrap.html', TAGS=TAGS, TWEETS=tweets)

@app.route("/tabs")
def tabs():
    return render_template('tabs.html')

if __name__ == "__main__":
    app.secret_key = 'super_secret_key'
    app.debug = True
    app.run(host='0.0.0.0', port=5000)
