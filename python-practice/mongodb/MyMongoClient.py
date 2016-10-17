from pymongo import MongoClient

class MyMongoClient:
    def __init__(self, dbname='tutorial', colName='test'):
        self.client = MongoClient()
        self.db = self.client.get_database(dbname)
        self.collection = self.db.get_collection(colName)

    def __getattr__(self, name):
        return getattr(self.collection, name)