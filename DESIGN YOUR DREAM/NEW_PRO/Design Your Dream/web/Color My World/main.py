from flask import *
from database import *
from admin import admin
from public import public
from designers import designers
from api import api


import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail

app=Flask(__name__)
app.secret_key="qwert"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(designers,url_prefix='/designers')
app.register_blueprint(api,url_prefix='/api')

app.run(debug=True,port=5870,host="0.0.0.0")