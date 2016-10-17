from datetime import datetime
import random
import bson
import inspect

doc = {
    '_id': bson.ObjectId(),
    'username': 'kbanker',
    'action_code': random.randrange(5),
    'time': datetime.utcnow(),
    'n': 1
}

encoded = bson.BSON.encode(doc)
print(inspect.getmembers(doc))
print("Length of encoded document:",len(encoded))

decoded = bson.BSON.decode(encoded)
print(decoded)
print(type(datetime.utcnow()))