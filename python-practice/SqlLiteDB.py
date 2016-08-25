__author__ = 'Darshan'

import sqlite3

def createConnection():
    conn = sqlite3.connect('example.db')
    return conn;

def createTable(conn):
    c = conn.cursor()
    # Create table
    c.execute('''CREATE TABLE stocks
                 (date text, trans text, symbol text, qty real, price real)''')

def insertData(conn):
    c = conn.cursor()
    # Insert a row of data
    c.execute("INSERT INTO stocks VALUES ('2006-01-05','BUY','RHAT',100,35.14)")
    # Save (commit) the changes
    conn.commit()

def selectData(conn):
    c = conn.cursor()
    c.execute("select * from stocks")
    # Save (commit) the changes
    dateOfPurchase, bsInd, stk, qty, prc = zip(c.fetchone())
    print(stk)

def closeConn(conn):
    # We can also close the connection if we are done with it.
    # Just be sure any changes have been committed or they will be lost.
    conn.close()

conn = createConnection()
# createTable(conn)
# insertData(conn)
selectData(conn)
closeConn(conn)