# securenative-java
SDK + agent for Secure Native paltform

Installation
Add the dependency to your pom.xml 

Option	Type	Optional	Default Value	Description
apiKey	string	false	none	SecureNative api key
apiUrl	string	true	https://api.securenative.com/v1/collector	Default api base address
interval	number	true	1000	Default interval for SDK to try to persist events
maxEvents	number	true	1000	Max in-memory events queue
timeout	number	true	1500	API call timeout in ms
autoSend	Boolean	true	true	Should api auto send the events
Event tracking


WebHook
Use verifyWebhook middleware to ensure that webhook is comming from SecureNative

