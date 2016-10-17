from mongodb import MyMongoClient

bulk = MyMongoClient(colName='test_bulk_inserts')
# client = MongoClient()
# db = client.get_database('tutorial')
# bulk = db.get_collection('test_bulk_inserts')

docs = [  # define an array of documents
    {'username': 'kbanker'},
    {'username': 'pbakkum'},
    {'username': 'sverch'}
]

ids = bulk.insert_many(docs)
print(ids.inserted_ids)
