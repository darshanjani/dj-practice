from flask import Flask

app = Flask(__name__)


@app.route("/")
def index():
    return "Index"


@app.route("/hello")
def hello():
    return "Hello World2"


@app.route("/greet/<user>")
def greet(user):
    return "Hello %s" % user


if __name__ == "__main__":
    app.secret_key = 'super_secret_key'
    app.debug = True
    app.run(host='0.0.0.0', port=5000)
