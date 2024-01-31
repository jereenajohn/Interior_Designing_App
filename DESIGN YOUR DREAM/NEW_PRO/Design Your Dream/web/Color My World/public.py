from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/',methods=['get','post'])
def home():
	return render_template("public/public_home.html")


@public.route('/login',methods=['get','post'])
def  login():
	if 'submit' in request.form:
		u=request.form['uname']
		p=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,p)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['usertype']=="admin":
				
				flash("successfully logged in")
				return redirect(url_for("admin.adminhome"))
			if res[0]['usertype']=="designer":
				flash('successfully logged in')
				return redirect(url_for("designers.designerhome"))
			if res[0]['usertype']=="pending":
				flash('Your account is not approved')
				return redirect(url_for("public.login"))


		else:
			flash('invalid username or password')
			
			

	return render_template("public/login.html")


@public.route('/reg',methods=['get','post'])
def reg():
	data={}

	if 'submit' in request.form:
		f=request.form['fname']
		l=request.form['lname']
		c=request.form['cname']
		pl=request.form['place']
		pi=request.form['pin']
		p=request.form['phn']
		e=request.form['email']	
		u=request.form['uname']
		pwd=request.form['pwd']

		q="select * from login where username='%s' and password='%s'"%(u,pwd)
		res=select(q)

		if len(res):
			flash("username and password already exist")
		else:

			q="insert into login values(null,'%s','%s','pending')"%(u,pwd)
			id=insert(q)

			q="insert into designers values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,f,l,c,pl,pi,p,e)
			insert(q)
			flash("Registered successfully")

	
	return render_template("public/designerreg.html",data=data)
		