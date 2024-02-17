Personal project for getting calendar availability from Google Calendar.  Makes it easier to show recruiters your availability for interviews.

Set up Google API Client: Ensure you have set up the Google API client in your project and authorized access to the Google Calendar API.
Download credential.json from Google Calendar API
Store in resources for local testing
Externalize for production deployment

Example usage:
Call ${HOST}:8080/avail
Returns times you are busy as JSON
