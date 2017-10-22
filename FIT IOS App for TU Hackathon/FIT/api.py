# mongo.py

from flask import Flask
from flask import jsonify
from flask import request
from pymongo import MongoClient

app = Flask(__name__)
 
app.config['MONGO_DBNAME'] = 'restdb'
app.config['MONGO_URI'] = 'mongodb://hackee:password1@54.193.64.159:27017/hackathon'

user="hackee"
password="password1"
host="54.193.64.159"
client = MongoClient("mongodb://" + user + ":" + password + "@" + host + ":27017/hackathon")
db = client.hackathon
cursor = db.creditfit.find()

if cursor.count() > 0:
   print("Success.")
else:
   "Failed"

@app.route('/getUserByID', methods=['GET'])
def getUserByID():
   print("*** In Get User By ID")
   #star = mongo.db.stars
   #output = []
   #for s in star.find():
   #   output.append({'UID' : s['name'], 'distance' : s['distance']})
   #return jsonify({'result' : output})

if __name__ == '__main__':
    app.run(debug=True)

