"""
Author: Joel Johnson
Date: 6/5/2021
Purpose: Final project for CS 493
Description: This is the REST backend for a child development app which includes
USERS, CHILDREN, and MILESTONES as entities. Provides basic REST functionality
to access these entities with authentication provided by OAuth.
"""
from flask import Flask, Blueprint, render_template, request, session, redirect, url_for
from google.cloud import datastore
import json

import milestones

app = Flask(__name__)
app.register_blueprint(milestones.bp)

client = datastore.Client()
	
# Send user to home page
@app.route('/')
def home():
	return render_template('home.html')

if __name__ == '__main__':
	app.run(host='127.0.0.1', port=8080, debug=True)