"""
Author: Joel Johnson
Date: 6/5/2021
Purpose: Final project for CS 493
Description: This is the REST backend for a child development app which includes
USERS, CHILDREN, and MILESTONES as entities. Provides basic REST functionality
to access these entities with authentication provided by OAuth.
"""
from flask import Flask, Blueprint, render_template, request, session, redirect, url_for
from flask_mail import Mail, Message
from google.cloud import datastore
import json

import milestones

app = Flask(__name__)
app.register_blueprint(milestones.bp)

client = datastore.Client()

# Open mail data
with open('email_info.json') as f:
	json_file = json.load(f)

# Set mail settings
mail_settings = {
    "MAIL_SERVER": 'smtp.gmail.com',
    "MAIL_PORT": 465,
    "MAIL_USE_TLS": False,
    "MAIL_USE_SSL": True,
    "MAIL_USERNAME": json_file['EMAIL_USER'],
    "MAIL_PASSWORD": json_file['EMAIL_PASSWORD']
}

RECIPIENT = json_file['RECIPIENT']
	
app.config.update(mail_settings)
mail = Mail(app)

# Send user to home page
@app.route('/')
def home():
	return render_template('index.html')
	
# Send user to developer page
@app.route('/about_me')
def about_me():
	return render_template('about_the_developer.html')
	
@app.route('/upload')
def upload():
	return render_template('upload.html')
	
# Send email 
@app.route('/contact_us', methods=["POST"])
def send_email():
	requiredKeys = ["contact_name", "contact_email", "contact_message"]
	for key in requiredKeys:
		if key not in request.form.keys():
			request.form[key] = "NONE"
		
	returnBody = "Name: {} Email: {} Message: {}".format(
			request.form["contact_name"], 
			request.form["contact_email"], 
			request.form["contact_message"]
		)

	with app.app_context():
		msg = Message(subject="Hello", 
			sender=app.config.get("MAIL_USERNAME"), 
			recipients=[RECIPIENT], 
			body=returnBody)
		mail.send(msg)
	
	return render_template('about_the_developer.html')
	
# Add user to email list
@app.route('/send_me_updates', methods=["POST"])
def add_email():
	requiredKeys = ["user_email", "user_name"]
	for key in requiredKeys:
		if key not in request.form.keys():
			request.form[key] = "NONE"

	# Set up entity and add to client
	new_email = datastore.Entity(key=client.key('email_list'))
	new_email.update({
		'user_email': request.form['user_email'],
		'user_name': request.form['user_name']
	})
	client.put(new_email)
	
	return render_template('index.html')

if __name__ == '__main__':
	app.run(host='127.0.0.1', port=8080, debug=True)