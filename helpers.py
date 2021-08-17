# Helper functions for milestones.py and children.py
import json
from six.moves.urllib.request import urlopen
from jose import jwt
from flask import Blueprint, jsonify

# Open secret client data 
with open('osu.us.auth0.json') as f:
	json_file = json.load(f)

ALGORITHMS = ["RS256"]
CLIENT_ID = json_file['client_id']
DOMAIN = json_file['domain']

bp = Blueprint('errors', __name__)

class AuthError(Exception):
    def __init__(self, error, status_code):
        self.error = error
        self.status_code = status_code

@bp.app_errorhandler(AuthError)
def handle_auth_error(ex):
    response = jsonify(ex.error)
    response.status_code = ex.status_code
    return response
	
def verify_content_type(request):
	# Accept/content types must be json or html, 406 if not
	if not request.accept_mimetypes['application/json'] or str(request.headers.get('Content-Type', 'application/json')) != 'application/json':
		raise AuthError({'Error': 'This API only supports JSON request and return objects.'}, 406)

# Verifies JWT is authentic and valid
def verify_jwt(request):
	if 'Authorization' not in request.headers:
		raise AuthError({"code": "invalid_header",
						"description":
							"Invalid header. "
							"No authorization provided"}, 401)
							
	auth_header = request.headers['Authorization'].split();
	token = auth_header[1]

	jsonurl = urlopen("https://"+ DOMAIN+"/.well-known/jwks.json")
	jwks = json.loads(jsonurl.read())
	try:
		unverified_header = jwt.get_unverified_header(token)
	except jwt.JWTError:
		raise AuthError({"code": "invalid_header",
						"description":
							"Invalid header. "
							"Use an RS256 signed JWT Access Token"}, 401)
	if unverified_header["alg"] == "HS256":
		raise AuthError({"code": "invalid_header",
						"description":
							"Invalid header. "
							"Use an RS256 signed JWT Access Token"}, 401)
	rsa_key = {}
	for key in jwks["keys"]:
		if key["kid"] == unverified_header["kid"]:
			rsa_key = {
				"kty": key["kty"],
				"kid": key["kid"],
				"use": key["use"],
				"n": key["n"],
				"e": key["e"]
			}
	if rsa_key:
		try:
			payload = jwt.decode(
				token,
				rsa_key,
				algorithms=ALGORITHMS,
				audience=CLIENT_ID,
				issuer="https://"+ DOMAIN+"/"
			)
		except jwt.ExpiredSignatureError:
			raise AuthError({"code": "token_expired",
							"description": "token is expired"}, 401)
		except jwt.JWTClaimsError:
			raise AuthError({"code": "invalid_claims",
							"description":
								"incorrect claims,"
								" please check the audience and issuer"}, 401)
		except Exception:
			raise AuthError({"code": "invalid_header",
							"description":
								"Unable to parse authentication"
								" token."}, 401)

		return payload
	else:
		raise AuthError({"code": "no_rsa_key",
							"description":
								"No RSA key in JWKS"}, 401)
