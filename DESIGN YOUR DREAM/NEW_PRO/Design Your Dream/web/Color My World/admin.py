from flask import *
from database import *

import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail


admin=Blueprint('admin',__name__)

@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template("admin/admin_home.html")


@admin.route('/viewdesigners',methods=['get','post'])
def viewdesigners():
	data={}
	q="select * from designers inner join login using (login_id)"
	res=select(q)
	data['viewdesigners']=res

	if "action" in request.args:
		action=request.args['action']
		lid=request.args['lid']

		if action=='accept':
			q="update login set usertype='designer' where login_id='%s' "%(lid)
			update(q)

			q="SELECT * FROM `designers` where login_id='%s'"%(lid)
			res=select(q)
			email=res[0]['email']
			email=email
			print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",email)
			pwd="YOU ARE ACTIVE NOW"
			print(pwd)
			try:

				gmail = smtplib.SMTP('smtp.gmail.com', 587)
				gmail.ehlo()
				gmail.starttls()
				gmail.login('hariharan0987pp@gmail.com','rjcbcumvkpqynpep')
			except Exception as e:
				print("Couldn't setup email!!"+str(e))
			pwd = MIMEText(pwd)
			pwd['Subject'] = 'Status'
			pwd['To'] = email
			pwd['From'] = 'hariharan0987pp@gmail.com'
			try:
				gmail.send_message(pwd)
				print(pwd)
				flash("EMAIL send SUCCESFULLY")
				flash('Accepted successfully')

			except Exception as e:
				print("COULDN'T SEND EMAIL", str(e))

			return redirect(url_for('admin.viewdesigners'))
		if action=='reject':
			q="delete from login where login_id='%s'"%(lid)
			update(q)
			q="SELECT * FROM `designers` where login_id='%s'"%(lid)
			res=select(q)
			email=res[0]['email']
			email=email
			print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",email)
			pwd="YOU ARE Not ACTIVE "
			print(pwd)
			try:

				gmail = smtplib.SMTP('smtp.gmail.com', 587)
				gmail.ehlo()
				gmail.starttls()
				gmail.login('hariharan0987pp@gmail.com','rjcbcumvkpqynpep')
			except Exception as e:
				print("Couldn't setup email!!"+str(e))
			pwd = MIMEText(pwd)
			pwd['Subject'] = 'Status'
			pwd['To'] = email
			pwd['From'] = 'hariharan0987pp@gmail.com'
			try:
				gmail.send_message(pwd)
				print(pwd)
				flash("EMAIL send SUCCESFULLY")
				flash('Rejected successfully')

			except Exception as e:
				print("COULDN'T SEND EMAIL", str(e))
			return redirect(url_for('admin.viewdesigners'))
			
		
	return render_template("admin/view_designers.html",data=data)

@admin.route('/designerreport')
def designerreport():

	data={}
	q1="select * from designers inner join login using (login_id) where usertype='designer'"
	ress=select(q1)
	data['record']=ress
	return render_template("admin/designerreport.html",data=data)


@admin.route('/viewsales',methods=['get','post'])
def viewsales():
	data={}
	lid=request.args['did']

	if "sale" in request.form:
		daily=request.form['daily']
		if request.form['monthly']=="":
			monthly=""
		else:
			monthly=request.form['monthly']+'%'
		print(monthly)
		
		q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id='%s' and proposal_status='confirm delivery' and (`proposal_date` like '%s'  ) or (`proposal_date` like '%s' ) "%(lid,monthly,daily)
		res=select(q)
		print(q)
		data['viewsales']=res
		session['res']=res
		r=session['res']
	else:
		# lid=request.args.get['did']
		q1="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id='%s' and proposal_status='confirm delivery'"%(lid)
		res=select(q1)
		data['viewsales']=res

	return render_template("admin/viewsales.html",data=data)

@admin.route('/sales',methods=['get','post'])
def sales():
	data={}
	lid=request.args['did']
	if "sale" in request.form:
		daily=request.form['daily']
		if request.form['monthly']=="":
			monthly=""
		else:
			monthly=request.form['monthly']+'%'
		print(monthly)
		
		q="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id='%s' and proposal_status='confirm delivery' and (`proposal_date` like '%s'  ) or (`proposal_date` like '%s' ) "%(lid,monthly,daily)
		res=select(q)
		print(q)
		data['sales']=res
		session['res']=res
		r=session['res']
	else:
		q1="select *,concat(users.first_name,' ',users.last_name)as uname from proposal inner join requirements using(requirement_id) inner join designes using(design_id) inner join users using(user_id) inner join designers on designers.designer_id=proposal.designer_id where proposal.designer_id='%s' and proposal_status='confirm delivery'"%(lid)
		res=select(q1)
		data['sales']=res

	return render_template("admin/sales.html",data=data)



@admin.route('/view_feedback',methods=['get','post'])
def view_feedback():
	data={}
	q="SELECT * FROM `feedback` INNER JOIN `users` USING(`user_id`)"
	res=select(q)
	data['feedback']=res
	return render_template("admin/view_feedback.html",data=data)


@admin.route('/viewusers',methods=['get','post'])
def viewusers():
	data={}
	q="select * from users"
	res=select(q)
	data['viewusers']=res
	return render_template("admin/view_users.html",data=data)

@admin.route('/userreport')
def userreport():

	data={}
	q1="select * from users"
	ress=select(q1)
	data['record']=ress
	return render_template("admin/userreport.html",data=data)



@admin.route('/viewdesigns',methods=['get','post'])
def viewdesigns():
	data={}
	q="select * from designes inner join designers using(designer_id) inner join materials using(material_id) where status<>'reject'"
	res=select(q)
	data['viewdesigns']=res
	if "action" in request.args:
		action=request.args['action']
		did=request.args['did']

		if action=='accept':
			q="update designes set status='accepted' where design_id='%s' "%(did)
			update(q)

			return redirect(url_for('admin.viewdesigns'))
		if action=='reject':
			q="update designes set status='Rejected' where design_id='%s'"%(did)
			update(q)
			return redirect(url_for('admin.viewdesigns'))
	return render_template("admin/view_uploads.html",data=data)


@admin.route('/designsreport')
def designsreport():

	data={}
	q1="select * from designes inner join designers using(designer_id) inner join materials using(material_id) where status= 'accepted'"
	ress=select(q1)
	data['record']=ress
	return render_template("admin/designsreport.html",data=data)


@admin.route('/viewrequirements',methods=['get','post'])
def viewrequirements():
	data={}
	if "sale" in request.form:
		daily=request.form['daily']
		if request.form['monthly']=="":
			monthly=""
		else:
			monthly=request.form['monthly']+'%'
		print(monthly)
		
		q="select *,concat(users.first_name,' ',users.last_name)as uname from requirements inner join users using(user_id) inner join designes using(design_id) inner join materials on materials.material_id=requirements.material_id inner join proposal using(requirement_id) where  (`proposal_date` like '%s'  ) or (`proposal_date` like '%s' )  "%(daily,monthly)
		res=select(q)
		print(q)
		data['viewrqrmnts']=res
		session['res']=res
		r=session['res']
	else:

		q="select *,concat(users.first_name,' ',users.last_name)as uname from requirements inner join users using(user_id) inner join designes using(design_id) inner join materials on materials.material_id=requirements.material_id inner join proposal using(requirement_id)"
		res=select(q)
		data['viewrqrmnts']=res
	return render_template("admin/view_requirements.html",data=data)
# @admin.route('/viewrequirements',methods=['get','post'])
# def viewrequirements():
# 	data={}
# 	q="select *,concat(users.first_name,' ',users.last_name)as uname from requirements inner join users using(user_id) inner join designes using(design_id) inner join materials on materials.material_id=requirements.material_id inner join proposal using(requirement_id)"
# 	res=select(q)
# 	data['viewrqrmnts']=res
# 	return render_template("admin/view_requirements.html",data=data)

@admin.route('/searchreport')
def searchreport():

	data={}
	q1="select *,concat(users.first_name,' ',users.last_name)as uname from requirements inner join users using(user_id) inner join designes using(design_id) inner join materials on materials.material_id=requirements.material_id inner join proposal using(requirement_id) where proposal_status='confirm delivery'"
	ress=select(q1)
	data['record']=ress
	return render_template("admin/searchreport.html",data=data)

 
@admin.route('/viewcomplaint',methods=['get','post'])
def viewcomplaint():
	data={}
	q="select * from complaint inner join users using(user_id)"
	res=select(q)
	data['viewcomplaint']=res
	j=0
	for i in range(1,len(res)+1):
		if 'submit'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			q="update complaint set reply='%s' where complaint_id='%s'"%(reply,res[j]['complaint_id'])
			update(q)
			flash("message send")
			return redirect(url_for("admin.viewcomplaint"))
		j=j+1
	return render_template("admin/view_complaints.html",data=data)


@admin.route('/admin_salesreport')
def admin_salesreport():
		data={}
		q="select *,concat(users.first_name,' ',users.last_name)as uname from requirements inner join users using(user_id) inner join designes using(design_id) inner join materials on materials.material_id=requirements.material_id inner join proposal using(requirement_id)"
		res=select(q)
		data['r']=res
		
		
		return render_template('admin/admin_salesreport.html',data=data)	